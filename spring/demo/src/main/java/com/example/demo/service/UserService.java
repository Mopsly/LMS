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

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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
        return this.userRepository.findAll().stream().map(
                (usr) -> new UserDto(usr.getId(),
                        usr.getUsername(),
                        usr.getEmail(),
                        usr.getNickname(),
                        "",
                        usr.getCourses(),
                        usr.getRoles()))
                .collect(Collectors.toList());
    }

    public UserDto findById(long id) {
        return userMapper.mapUserToDto((User) this.userRepository.findById(id).orElseThrow(NotFoundException::new));
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
        // если приходит запрос на сохранение пользователя без списка ролей,
        // то ищем его первоначальный список ролей и сохраняем его
        if (userDto.getRoles()==null){
            Set<Role> roles = findById(userDto.getId()).getRoles();
            userDto.setRoles(roles);
        }
        this.userRepository.save(new User(userDto.getId(), userDto.getUsername(), userDto.getEmail(), userDto.getNickname(),
                this.encoder.encode(userDto.getPassword()), userDto.getCourses(), userDto.getRoles()));
    }

    public UserDto findUserByUsername(String username) {
        return this.userMapper.mapUserToDto(userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new));
    }
}
