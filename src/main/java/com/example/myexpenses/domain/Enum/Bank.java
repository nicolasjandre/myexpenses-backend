package com.example.myexpenses.domain.Enum;

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

    @Override
    public String toString() {
        return this.name();
    }
}
