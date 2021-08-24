package com.example.demo.validator;

import com.example.demo.annotations.UniqueUsername;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, UserDto> {

    private final UserRepository userRepository;

    @Autowired
    public UniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
            long idVal = -1L;

            if (idStr != null){
                idVal = Long.parseLong(idStr);
            }

            Optional<User> user = userRepository.findUserByUsername(usernameVal);
            // Проверка нахождения пользователя в бд
            if (user.isPresent()){
                // Проверка что email принадлежит тому же пользователю
                Long id = user.get().getId();
                return id.equals(idVal);
            }
            return true;
        } catch (IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException ignore) {
            // ignore
        }
        return true;
    }
}
