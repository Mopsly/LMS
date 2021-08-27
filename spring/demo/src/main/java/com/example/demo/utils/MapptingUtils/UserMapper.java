package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;

    @Autowired
    public UserMapper(PasswordEncoder encoder, ModelMapper mapper) {
        this.encoder = encoder;
        this.mapper = mapper;
    }

    public User mapDtoToUser(UserDto userDto) {
        if (userDto == null)
            return null;
        return new User(userDto.getId(), userDto.getUsername(), userDto.getEmail(), userDto.getNickname(),
                encoder.encode(userDto.getPassword()), userDto.getCourses(), userDto.getRoles(), userDto.getPhone());
    }

    public UserDto mapUserToDto(User user) {
        return user == null? new UserDto(): mapper.map(user,UserDto.class);
    }
}
