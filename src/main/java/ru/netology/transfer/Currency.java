package ru.netology.transfer;

public enum Currency {
    RUB("Российский рубль"),
    RUR("Рубль"),
    KZT("Казахстанский тенге"),
    UZS("Узбекский сум"),
    CNY("Китайский юань");
    private String currency;

    Currency (String currency) {
        this.currency = currency;
    }

    public String getCommand() {
        return currency;
    }
}
