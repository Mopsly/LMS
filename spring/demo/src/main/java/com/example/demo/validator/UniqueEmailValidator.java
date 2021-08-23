package com.example.demo.validator;

import com.example.demo.annotations.UniqueEmail;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserDto> {


    private String email;
    private String id;
    @Autowired
    private UserRepository userRepository;

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
            Long idVal = -1L;
            if (idStr != null){
                idVal = Long.valueOf(idStr);
            }
            User user = userRepository.getUserByEmail(emailVal);
            return (user == null || user.getId().equals(idVal));
        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }
}
