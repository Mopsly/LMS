package com.example.demo.service;

import com.example.demo.dao.CourseRepository;
import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import com.example.demo.utils.MapptingUtils.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

public class UserServiceTest {

    UserService userService;
    List<User> users = List.of(new User(1L,"Вася","123",new HashSet<>(),new HashSet<>()),
            new User(2L,"Петя","123",new HashSet<>(),new HashSet<>()));

    @BeforeEach
    public void setUp(){
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        RoleRepository roleRepositoryMock = Mockito.mock(RoleRepository.class);
        CourseRepository courseRepositoryMock = Mockito.mock(CourseRepository.class);
        PasswordEncoder passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        UserMapper userMapper =  new UserMapper(passwordEncoderMock);
        CourseService courseService =  new CourseService(courseRepositoryMock);


        Mockito.when(userRepositoryMock.findAll()).thenReturn(users);
        Mockito.when(userRepositoryMock.findById(1L))
                .thenReturn(java.util.Optional.of(new User(1L, "Вася", "123", new HashSet<>(), new HashSet<>())));

        Mockito.when(userRepositoryMock.findUserByUsername("Вася"))
                .thenReturn(java.util.Optional.of(new User(1L, "Вася", "123", new HashSet<>(), new HashSet<>())));

        userService = new UserService(userRepositoryMock,passwordEncoderMock,userMapper,roleRepositoryMock,courseService);
    }

    @Test
    public void findAllTest(){
        Assertions.assertThat(userService.findAll().size()).isEqualTo(2);
        Assertions.assertThat(userService.findAll()).extracting("username").contains("Вася");
        Assertions.assertThat(userService.findAll()).extracting("username").contains("Петя");
    }

    @Test
    public void findByIdTest(){
        Assertions.assertThat(userService.findById(1L)).extracting("username").isEqualTo("Вася");
        Assertions.assertThat(userService.findById(1L)).extracting("password").isNotEqualTo("123");
    }

    @Test
    public void findByUsernameTest(){
        Assertions.assertThat(userService.findUserByUsername("Вася")).extracting("username").isEqualTo("Вася");
    }

}
