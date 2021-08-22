package com.example.demo.validator;

import com.example.demo.annotations.UniqueEmail;
import com.example.demo.annotations.UniqueUsername;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private  UserService userService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
//        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return userService.getUserByUsername(value) == null;
    }

}
