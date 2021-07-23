package com.example.demo.validator;

import com.example.demo.annotations.Lang;
import com.example.demo.annotations.TitleCase;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class TitleCaseValidator implements ConstraintValidator<TitleCase, String> {

    private Lang lang;

    private static final String[] enLowerCaseWords = {"a", "but", "or", "not", "the", "an", "for", "of", "at"};
    private static final String[] forbiddenSymbols = {"\r", "\t", "\n"};
    private static final String allowedSymbols = "\" ',:";

    @Override
    public void initialize(TitleCase constraintAnnotation) {
        lang = constraintAnnotation.lang();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if ((value == null) || (value.isEmpty())) {
            return false;
        }

        switch (lang) {
            case RU:
                return isRuValid(isGeneralValid(value), value);
            case EN:
                return isEnValid(isGeneralValid(value), value);
            default:
                TitleCaseValidatorDto generalValidDto = isGeneralValid(value);
                return isEnValid(generalValidDto, value) || isRuValid(generalValidDto, value);

        }
    }

    public TitleCaseValidatorDto isGeneralValid(String value) {
        TitleCaseValidatorDto validatorDto = new TitleCaseValidatorDto();
        // проверка запрещенных символов
        for (String symbol : forbiddenSymbols) {
            if (value.contains(symbol)) {
                validatorDto.setGeneralValid(false);
                return validatorDto;
            }
        }
        // проверка первого и последнего символа на пробел
        if (value.charAt(0) == ' ' || value.charAt(value.length() - 1) == ' ') {
            validatorDto.setGeneralValid(false);
            return validatorDto;
        }
        //проверка на несколько пробелов подряд
        if (value.contains("  ")) {
            validatorDto.setGeneralValid(false);
            return validatorDto;
        }

        //проверка на сочетание русских и английских символов
        //плюс проверка на допустимость символов
        for (char symbol : value.toCharArray()) {
            // проверка на то что символ является буквой
            if (Character.isLetter(symbol)) {
                Character.UnicodeBlock block = Character.UnicodeBlock.of(symbol);
                //проверка на англ. или рус. букву
                if (Character.UnicodeBlock.CYRILLIC.equals(block)) {
                    validatorDto.setRuLang(true);
                } else if (Character.UnicodeBlock.BASIC_LATIN.equals(block)) {
                    validatorDto.setEnLang(true);
                } else {           //если какая-то другая буква - невалидно
                    validatorDto.setGeneralValid(false);
                    return validatorDto;
                }
            } else  // если не буква, то проверка на доп символы
                if (allowedSymbols.indexOf(symbol) < 0) {
                    validatorDto.setGeneralValid(false);
                    return validatorDto;
                }
        }
        validatorDto.setGeneralValid(validatorDto.isEnLang() != validatorDto.isRuLang());
        return validatorDto;
    }


    public boolean isRuValid(TitleCaseValidatorDto validatorDto, String value) {
        //проверка что подан действительно русский
        if (!validatorDto.isRuLang() || !validatorDto.isGeneralValid()) return false;
        List<String> wordList = Arrays.asList(value.split(" "));
        //  Если первое слово начинается с маленькой буквы - невалидно
        if (Character.isLowerCase(wordList.get(0).charAt(0))) {
            return false;
        }
        // Если слова начиная с первого начинаются с большой буквы - невалидно
        for (int i = 1; i < wordList.size(); i++) {
            if (Character.isUpperCase(wordList.get(i).charAt(0))) {
                return false;
            }
        }
        return true;
    }

    public boolean isEnValid(TitleCaseValidatorDto validatorDto, String value) {
        //проверка что подан действительно английский
        if (!validatorDto.isEnLang() || !validatorDto.isGeneralValid()) return false;
        List<String> lowerCaseWordList = Arrays.asList(enLowerCaseWords);
        List<String> wordList = Arrays.asList(value.split(" "));
        //Если первое и последнее слово - не с большой буквы - невалидно
        if (Character.isLowerCase(wordList.get(0).charAt(0))
                || Character.isLowerCase(wordList.get(wordList.size() - 1).charAt(0))) {
            return false;
        }
        for (String word : wordList) {
            // Если слово не содержится в спике предлогов и союзов - проверка на то что оно с большой буквы
            if (!lowerCaseWordList.contains(word)
                    && Character.isLowerCase(word.charAt(0))) {
                return false;
            }
        }
        return true;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }
}
