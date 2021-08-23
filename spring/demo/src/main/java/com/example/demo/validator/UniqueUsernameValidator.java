package com.example.demo.validator;

import com.example.demo.annotations.UniqueUsername;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, UserDto> {

    @Autowired
    private UserRepository userRepository;

    private String username;
    private String id;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        username = constraintAnnotation.username();
        id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            final String usernameVal = BeanUtils.getProperty(value, username);
            final String idStr = BeanUtils.getProperty(value, id);
            Long idVal = -1L;
            if (idStr != null){
                idVal = Long.valueOf(idStr);
            }

            User user = userRepository.getUserByUsername(usernameVal);
            return (user == null || user.getId().equals(idVal));
        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }
}
