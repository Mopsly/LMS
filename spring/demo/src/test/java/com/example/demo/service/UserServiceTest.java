package com.example.demo.service;

import com.example.demo.dao.CourseRepository;
import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import com.example.demo.utils.MapptingUtils.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserService userService;
    List<User> users = List.of(new User(1L,"Вася","email","nickname","123",new HashSet<>(),new HashSet<>()),
            new User(2L,"Петя","email","nickname","123",new HashSet<>(),new HashSet<>()));

    @BeforeEach
    public void setUp(){
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        RoleRepository roleRepositoryMock = Mockito.mock(RoleRepository.class);
        CourseRepository courseRepositoryMock = Mockito.mock(CourseRepository.class);
        PasswordEncoder passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        UserMapper userMapper =  new UserMapper(passwordEncoderMock);
        CourseService courseService =  new CourseService(courseRepositoryMock);


        when(userRepositoryMock.findAll()).thenReturn(users);
        when(userRepositoryMock.findById(1L))
                .thenReturn(java.util.Optional.of(new User(1L, "Вася","email","nickname", "123", new HashSet<>(), new HashSet<>())));

        when(userRepositoryMock.findUserByUsername("Вася"))
                .thenReturn(java.util.Optional.of(new User(1L, "Вася","email","nickname", "123", new HashSet<>(), new HashSet<>())));
        when(userRepositoryMock.findUsersNotAssignedToCourse(any(Long.class)))
                .thenReturn(List.of(new User(1L, "Вася","email","nickname", "123", new HashSet<>(), new HashSet<>())));

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
        Assertions.assertThat(userService.findDtoById(1L)).extracting("username").isEqualTo("Вася");
        Assertions.assertThat(userService.findDtoById(1L)).extracting("password").isNotEqualTo("123");
    }

    @Test
    public void findByUsernameTest(){
        Assertions.assertThat(userService.findUserByUsername("Вася")).extracting("username").isEqualTo("Вася");
    }

    @Test
    public void unassignedUsersTest(){
        Assertions.assertThat(userService.unassignedUsers(1L).size()).isEqualTo(1);
        Assertions
                .assertThat(userService.unassignedUsers(1L))
                .extracting("username").contains("Вася");
    }

}
