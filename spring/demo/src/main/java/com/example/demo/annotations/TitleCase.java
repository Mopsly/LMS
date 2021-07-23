package com.example.demo.annotations;

import com.example.demo.validator.TitleCaseValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TitleCaseValidator.class)
public @interface TitleCase{

    Lang lang() default Lang.ANY;

    String message() default "incorrect Tittle";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
