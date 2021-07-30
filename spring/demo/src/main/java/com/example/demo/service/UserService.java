package com.example.demo.service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.utils.MapptingUtils.UserMapper;
import java.util.Iterator;
import java.util.List;
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
        return (List)this.userRepository.findAll().stream().map((usr) -> {
            return new UserDto(usr.getId(), usr.getUsername(), "", usr.getCourses(), usr.getRoles());
        }).collect(Collectors.toList());
    }

    public UserDto findById(long id) {
        return this.userMapper.mapUserToDto((User)this.userRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public List<User> unassignedUsers(Long courseId) {
        return this.userRepository.findUsersNotAssignedToCourse(courseId);
    }

    public void deleteById(long id) {
        User user = (User)this.userRepository.findById(id).orElseThrow(NotFoundException::new);
        Iterator var4 = user.getCourses().iterator();

        while(var4.hasNext()) {
            Course course = (Course)var4.next();
            this.courseService.removeUserFromCourse(user, course);
        }

        this.userRepository.deleteById(id);
    }

    public void save(UserDto userDto) {
        this.userRepository.save(new User(userDto.getId(), userDto.getUsername(), this.encoder.encode(userDto.getPassword()), userDto.getCourses(), userDto.getRoles()));
    }

    public UserDto findUserByUsername(String username) {
        return this.userMapper.mapUserToDto((User)this.userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new));
    }
}
