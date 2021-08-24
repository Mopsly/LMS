package com.example.demo.validator;

import com.example.demo.annotations.UniqueEmail;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
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
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try{
            final String emailVal = BeanUtils.getProperty(value, email);
            final String idStr = BeanUtils.getProperty(value, id);
            Long idVal = -1L;
            if (idStr != null){
                idVal = Long.valueOf(idStr);
            }
            Optional<User> user = userRepository.findUserByEmail(emailVal);
            return !user.isPresent();
        }catch (Exception e){

        }
        return false;
    }
}
