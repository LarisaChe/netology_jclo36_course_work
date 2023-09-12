package ru.netology.repository;

import org.junit.jupiter.api.*;
import ru.netology.exception.ErrorConfirmation;
import ru.netology.transfer.Amount;
import ru.netology.transfer.Currency;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TransferMoneyRepositoryTest {

    public TransferMoneyRepository transferMoneyRepository = new TransferMoneyRepository();
    public TransferMoney testTransfer = new TransferMoney(
            "1440111456121113",
            "12/29",
            "321",
            "2550222122222223",
            new Amount(500, Currency.RUB));

    @Test
    public void test11_AddOperation()  {
        Assertions.assertDoesNotThrow(() -> transferMoneyRepository.addOperation(testTransfer));
    }

    @Test
    public void test12_SaveVerificationCode() throws IOException {
        String operationId = transferMoneyRepository.saveVerificationCode("1", "1234");
        Assertions.assertEquals("1", operationId);
    }

    @Test
    public void test13_GetOperations() {
        Assertions.assertDoesNotThrow(() -> transferMoneyRepository.getOperations());
    }

    @Test
    public void test14_SaveVerificationCode_throwsException() {
        Assertions.assertThrows(ErrorConfirmation.class, () -> transferMoneyRepository.saveVerificationCode("13", "1313"));
    }

    @Test
    public void test15_printOperations() {
        Assertions.assertDoesNotThrow(() -> transferMoneyRepository.printOperations());
    }

    @Test
    public void test16_getOperations() {
        Assertions.assertDoesNotThrow(() -> transferMoneyRepository.getOperations());
    }
}
