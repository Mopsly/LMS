package com.example.demo.validator;

import com.example.demo.annotations.LatinAndSymbols;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatinAndSymbolsValidator implements ConstraintValidator<LatinAndSymbols, String> {


    @Override
    public void initialize(LatinAndSymbols constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        for (int i = 0; i<value.length(); i++){
            if (!Character.UnicodeBlock.of(value.charAt(i)).equals(Character.UnicodeBlock.BASIC_LATIN)
                    || Character.isDigit(value.charAt(i)))
                return false;
        }
        return true;
    }

}
