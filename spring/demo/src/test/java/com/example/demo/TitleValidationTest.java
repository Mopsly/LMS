package com.example.demo;

import com.example.demo.annotations.Lang;
import com.example.demo.validator.TitleCaseValidator;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertEquals;

public class TitleValidationTest {

private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void testRuValid(){
        TitleCaseValidator validator= new TitleCaseValidator();
        validator.setLang(Lang.RU);
        boolean result = validator.isValid("Имя курса",constraintValidatorContext);
        assertEquals (result,true);
    }

    @Test
    public void testEnValid(){
        TitleCaseValidator validator= new TitleCaseValidator();
        validator.setLang(Lang.EN);
        boolean result = validator.isValid("The Man, Who Sold the World",constraintValidatorContext);
        assertEquals (result,true);
    }

    @Test
    public void testRuWrongCase(){
        TitleCaseValidator validator= new TitleCaseValidator();
        validator.setLang(Lang.RU);
        boolean result = validator.isValid("Имя Курса",constraintValidatorContext);
        assertEquals (result,false);
    }

    @Test
    public void testEnWrongCase(){
        TitleCaseValidator validator= new TitleCaseValidator();
        validator.setLang(Lang.EN);
        boolean result = validator.isValid("Course name",constraintValidatorContext);
        assertEquals (result,false);
    }

    @Test
    public void testWrongSymbols(){
        TitleCaseValidator validator= new TitleCaseValidator();
        validator.setLang(Lang.ANY);
        boolean result = validator.isValid("Panic! at the Disco",constraintValidatorContext);
        assertEquals (result,false);
    }
    @Test
    public void testWrongSpaces(){
        TitleCaseValidator validator= new TitleCaseValidator();
        validator.setLang(Lang.ANY);
        boolean result = validator.isValid(" Course Name ",constraintValidatorContext);
        assertEquals (result,false);
    }

    @Test
    public void testWrongSpaces2(){
        TitleCaseValidator validator= new TitleCaseValidator();
        validator.setLang(Lang.ANY);
        boolean result = validator.isValid("Course  Name",constraintValidatorContext);
        assertEquals (result,false);
    }

    @Test
    public void testMixedLang(){
        TitleCaseValidator validator= new TitleCaseValidator();
        validator.setLang(Lang.ANY);
        boolean result = validator.isValid("Course имя",constraintValidatorContext);
        assertEquals (result,false);
    }
    @Test
    public void testNull(){
        TitleCaseValidator validator= new TitleCaseValidator();
        validator.setLang(Lang.ANY);
        RuntimeException generatedException = null;
        try {
            boolean result = validator.isValid(null, constraintValidatorContext);
        }catch (RuntimeException e){
            generatedException = e;
        }
        assertEquals (generatedException.getMessage(),"Validated value is null");
    }
}
