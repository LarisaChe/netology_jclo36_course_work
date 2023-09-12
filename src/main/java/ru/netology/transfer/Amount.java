package ru.netology.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Amount {

    @NotNull
    @JsonProperty("currency")
    private final Currency currency;

    @NotNull
    @JsonProperty("value")
    @Min(1)
    private final int value;

    public Amount(int value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public int getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }


    @Override
    public String toString() {
        return "Amount = " + value + " " + currency;
    }
}
