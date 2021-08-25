package com.example.demo.mappingUtils;

import com.example.demo.dao.CourseRepository;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.utils.MapptingUtils.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

public class UserMapperTest {

    UserMapper userMapper;

    @BeforeEach
    public void setUp(){
        PasswordEncoder passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        Mockito.when(passwordEncoderMock.encode("123")).thenReturn("***");
        userMapper = new UserMapper(passwordEncoderMock);
    }

    @Test
    public void userToDtoTest(){
        User user = new User(1L,"username","email","nickname","password",new HashSet<>(),new HashSet<>(), "88005553535");
        UserDto userDto =  userMapper.mapUserToDto(user);

        Assertions.assertThat(userDto.getId()).isEqualTo(user.getId());
        Assertions.assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(userDto.getPassword()).isEqualTo("***");
    }

    @Test
    public void dtoToUserTest(){
        UserDto userDto = new UserDto(1L,"username","email","nickname","123","123",new HashSet<>(),new HashSet<>(), "88005553535");
        User user =  userMapper.mapDtoToUser(userDto);

        Assertions.assertThat(user.getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(user.getUsername()).isEqualTo(userDto.getUsername());
        Assertions.assertThat(user.getPassword()).isEqualTo("***");
    }
}
