package com.example.myexpenses.domain.enums;

public enum Type {

    INCOME("A receber"),
    EXPENSE("A pagar");

    private String value;

    private Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}