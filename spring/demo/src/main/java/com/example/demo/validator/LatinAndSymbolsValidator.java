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
            char c = value.charAt(i);
            Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
            Character.UnicodeBlock latinBlock = Character.UnicodeBlock.BASIC_LATIN;
            //Если не латиница и не символ
            //Или если цифра
            if (!block.equals(latinBlock) || Character.isDigit(c))
                return false;
        }
        return true;
    }

}
