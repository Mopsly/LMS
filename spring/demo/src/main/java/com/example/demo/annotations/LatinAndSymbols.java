package com.example.demo.annotations;

import com.example.demo.validator.LatinAndSymbolsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = LatinAndSymbolsValidator.class)
public @interface LatinAndSymbols {


    String message() default "Поле должно содержать латиницу и/или спец.символы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
