package ru.netology.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Amount {

    @NotNull
    @JsonProperty("currency")
    private Currency currency;

    @NotNull
    @JsonProperty("value")
    @Min(1)
    private int value;

    public Amount(int value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Amount() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Amount = " + value + " " + currency;
    }
}
