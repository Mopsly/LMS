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
        if (userDto == null)
            return null;
        return new User(userDto.getId(), userDto.getUsername(), userDto.getEmail(), userDto.getNickname(),
                encoder.encode(userDto.getPassword()), userDto.getCourses(), userDto.getRoles());
    }

    public UserDto mapUserToDto(User user) {
        if (user == null)
            return null;
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getNickname(),
                "***","***", user.getCourses(),
                user.getRoles());
    }
}
