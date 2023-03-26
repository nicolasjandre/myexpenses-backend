package com.example.myexpenses.domain.Enum;

public enum TitleType {

    INCOME("A receber"),
    EXPENSE("A pagar");

    private String value;

    private TitleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}