package com.example.demo.validator;

import com.example.demo.annotations.Lang;
import com.example.demo.annotations.TitleCase;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class TitleCaseValidator implements ConstraintValidator<TitleCase, String> {

    private Lang lang;

    private boolean eng = false;
    private boolean ru = false;

    private final String[] enLowerCaseWords = {"a", "but", "or", "not", "the", "an", "for","of","at"};
    private final String[] forbiddenSymbols = {"\r", "\t", "\n"};
    private final char[] allowedSymbols = {'"', '\'', ',', ':', ' '};

    @Override
    public void initialize(TitleCase constraintAnnotation) {
        lang = constraintAnnotation.lang();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        switch (lang) {
            case RU:
                return isGeneralValid(value) && isRuValid(value);
            case EN:
                return isGeneralValid(value) && isEnValid(value);
            default:
                return isGeneralValid(value);
        }
    }

    public boolean isGeneralValid(String value) {
        // проверка запрещенных символов
        for (String symbol : forbiddenSymbols) {
            if (value.contains(symbol)) {
                return false;
            }
        }
        // проверка первого и последнего символа на пробел
        if (value.charAt(0) == ' ' || value.charAt(value.length() - 1) == ' ') {
            return false;
        }
        //проверка на несколько пробелов подряд
        if (value.contains("  "))
            return false;

        //проверка на сочетание русских и английских символов
        //плюс проверка на допустимость символов
        for (char symbol : value.toCharArray()) {
            // проверка на то что символ является буквой
            if (Character.isLetter(symbol)) {
                Character.UnicodeBlock block = Character.UnicodeBlock.of(symbol);
                //проверка на англ. или рус. букву
                if (Character.UnicodeBlock.CYRILLIC.equals(block)) {
                    ru = true;
                } else if (Character.UnicodeBlock.BASIC_LATIN.equals(block)) {
                    eng = true;
                }
                //если какая-то другая буква - невалидно
                else{
                    return false;
                }
            }
                // если не буква, то проверка на доп символы
            else
            if (new String(allowedSymbols).indexOf(symbol) < 0)
                return false;
        }
        return eng!=ru;
    }


    public boolean isRuValid(String value) {
        //проверка что подан действительно русский
        if (!ru) return false;
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

    public boolean isEnValid(String value) {
        //проверка что подан действительно английский
        if (!eng) return false;
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
