package com.example.myexpenses.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum Bank {

    NUBANK("NuBank"),
    PLAYERS_BANK("PlayersBank"),
    BANCO_DO_BRASIL("Banco do Brasil"),
    ITAU("Itau"),
    BTG_PACTUAL("BTG"),
    BANCO_INTER("Banco Inter"),
    BANCO_PAN("Banco Pan"),
    BRADESCO("Bradesco"),
    CAIXA_ECONOMICA("Caixa Econômica"),
    C6_BANK("C6 Bank"),
    SANTANDER("Santander"),
    WILL_BANK("Will Bank"),
    XP("XP"),
    NEON("Neon"),
    NEXT("Next"),
    DIGIO("Digio"),
    BANCO_ORIGINAL("Banco Original");
    
    private String value;

    private Bank(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Map<String, String> getAllBanks() {
        Map<String, String> banks = new HashMap<>();
        for (Bank bank : Bank.values()) {
            banks.put(bank.name(), bank.getValue());
        }
        return banks;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
