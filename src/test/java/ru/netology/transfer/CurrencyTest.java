package ru.netology.transfer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.transfer.Currency;
import ru.netology.transfer.Status;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CurrencyTest {
    @ParameterizedTest
    @EnumSource(Currency.class)
    void testGetCommand(Currency currency) {
        assertNotNull(currency);
    }
}
