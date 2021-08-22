package com.example.demo.annotations;

import com.example.demo.validator.TitleCaseValidator;
import com.example.demo.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

    String message() default "Введенный адрес почты уже занят";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
