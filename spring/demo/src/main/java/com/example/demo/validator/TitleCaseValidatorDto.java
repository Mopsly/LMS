package com.example.demo.validator;

public class TitleCaseValidatorDto {
    private boolean generalValid;
    private boolean ruLang;
    private boolean enLang;

    public TitleCaseValidatorDto() {
        generalValid = false;
        ruLang = false;
        enLang = false;
    }

    public boolean isGeneralValid() {
        return generalValid;
    }

    public void setGeneralValid(boolean generalValid) {
        this.generalValid = generalValid;
    }

    public boolean isRuLang() {
        return ruLang;
    }

    public void setRuLang(boolean ruLang) {
        this.ruLang = ruLang;
    }

    public boolean isEnLang() {
        return enLang;
    }

    public void setEnLang(boolean enLang) {
        this.enLang = enLang;
    }

    public TitleCaseValidatorDto(boolean generalValid, boolean ruLang, boolean enLang) {
        this.generalValid = generalValid;
        this.ruLang = ruLang;
        this.enLang = enLang;
    }
}
