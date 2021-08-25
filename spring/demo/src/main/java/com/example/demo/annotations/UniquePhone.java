package com.example.demo.annotations;

import com.example.demo.validator.UniquePhoneValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniquePhoneValidator.class)
public @interface UniquePhone {

    String message() default "Введенный номер телефона уже занят";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
