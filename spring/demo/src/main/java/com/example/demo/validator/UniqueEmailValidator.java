package com.example.demo.validator;

import com.example.demo.annotations.UniqueEmail;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserDto> {

    private final UserRepository userRepository;


    private String email;
    private String id;
    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        email = constraintAnnotation.email();
        id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            final String emailVal = BeanUtils.getProperty(value, email);
            final String idStr = BeanUtils.getProperty(value, id);
            long idVal = -1L;

            if (idStr != null){
                idVal = Long.parseLong(idStr);
            }

            Optional<User> user = userRepository.findUserByEmail(emailVal);
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
