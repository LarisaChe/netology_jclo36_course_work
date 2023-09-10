package ru.netology.transfer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.transfer.Status;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StatusTest {
    @ParameterizedTest
    @EnumSource(Status.class)
    void testGetCommand(Status status) {
        assertNotNull(status);
    }
}

