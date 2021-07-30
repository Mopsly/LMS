package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private final PasswordEncoder encoder;

    @Autowired
    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public User mapDtoToUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getUsername(), this.encoder.encode(userDto.getPassword()), userDto.getCourses(), userDto.getRoles());
    }

    public UserDto mapUserToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), "*******", user.getCourses(), user.getRoles());
    }
}
