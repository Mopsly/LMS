package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.UserAuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertThrows;


public class UserAuthServiceTest {
    UserAuthService userAuthService;

    @BeforeEach
    public void setUp(){
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        Mockito.when(userRepositoryMock.findUserByUsername("username"))
                .thenReturn(java.util.Optional.of(new User(
                        1L,
                        "username","email","nickname",
                        "password", new HashSet<>(), Set.of(new Role(1L,"ROLE_USER",new HashSet<>())), "88005553535")));
        userAuthService =  new UserAuthService(userRepositoryMock);
    }

    @Test
    public void loadByUsernameTest(){
       UserDetails user = userAuthService.loadUserByUsername("username");
       Assertions.assertThat(user.getUsername()).isEqualTo("username");
       Assertions.assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    public void loadByUsernameNullTest(){
        assertThrows(UsernameNotFoundException.class, () -> userAuthService.loadUserByUsername(""));
    }
}
