package com.example.demo.annotations;

import com.example.demo.validator.TitleCaseValidator;
import com.example.demo.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

    String message() default "Введенный адрес почты уже занят";

    String email();

    String id();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
