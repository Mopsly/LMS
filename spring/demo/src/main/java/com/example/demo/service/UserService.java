package com.example.demo.service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.utils.MapptingUtils.UserMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final CourseService courseService;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, UserMapper userMapper, RoleRepository roleRepository, CourseService courseService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
        this.courseService = courseService;
    }

    public List<UserDto> findAll() {
        return this.userRepository.findAll().stream()
                .map(userMapper::mapUserToDto)
                .collect(Collectors.toList());
    }

    public UserDto findDtoById(long id) {
        return userMapper.mapUserToDto(this.userRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public User findById(Long id){
        return userRepository.getById(id);
    }

    public List<User> unassignedUsers(Long courseId) {
        return userRepository.findUsersNotAssignedToCourse(courseId);
    }

    public void deleteById(long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);

        if (user.getCourses() != null) {
            for (Course course : user.getCourses()){
                this.courseService.removeUserFromCourse(user, course);
            }
        }
        this.userRepository.deleteById(id);
    }

    public void save(UserDto userDto) {
        // ???????? ???????????????? ???????????? ???? ???????????????????? ???????????????????????? ?????? ???????????? ??????????,
        // ???? ???????? ?????? ???????????????????????????? ???????????? ?????????? ?? ?????????????????? ??????
        if (userDto.getRoles()==null){
            Set<Role> roles = findDtoById(userDto.getId()).getRoles();
            userDto.setRoles(roles);
        }
        this.userRepository.save(new User(userDto.getId(), userDto.getUsername(), userDto.getEmail(), userDto.getNickname(),
                this.encoder.encode(userDto.getPassword()), userDto.getCourses(), userDto.getRoles(), userDto.getPhone()));
    }
    public void update(UserDto userDto) {
        User user = userRepository.getById(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(this.encoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setNickname(userDto.getNickname());
        userRepository.save(user);

    }

    public UserDto findUserByUsername(String username)
    {
        return this.userMapper.mapUserToDto(userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new));
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new);
    }

}
