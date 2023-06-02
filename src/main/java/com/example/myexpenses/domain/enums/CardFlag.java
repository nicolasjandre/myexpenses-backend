package com.example.myexpenses.domain.enums;

public enum CardFlag {

    MASTERCARD("Mastercard"),
    VISA("Visa"),
    ELO("ELO"),
    AMERICAN_EXPRESS("American Express"),
    HIPERCARD("Hipcard");

    private String value;

    private CardFlag(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
