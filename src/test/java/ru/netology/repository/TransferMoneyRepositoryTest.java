package ru.netology.repository;

import org.junit.jupiter.api.*;
import ru.netology.exception.ErrorOperationId;
import ru.netology.repository.TransferMoneyRepository;
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

    private String testId = "1";

    public void setTestId(String testId) {
        this.testId = testId;
    }

    @Test
    public void test10() {
        int n = transferMoneyRepository.countOperations() + 1;
        setTestId(String.valueOf(n + 1));
    }

    @Test
    public void test11_AddOperation() throws IOException {
        String operationId = transferMoneyRepository.addOperation(testTransfer);
        Assertions.assertEquals(testId, operationId);
    }


    @Test
    public void test12_SaveVerificationCode() throws ErrorOperationId, IOException {
        String operationId = transferMoneyRepository.saveVerificationCode(testId, "1234");
        Assertions.assertEquals(testId, operationId);
    }

    @Test
    public void test13_GetOperations() {
        if (testId.equals("1")) {
            String testStr = "{1=TransferMoney{cardFrom=1440111456121113 / 12/29 / 321, cardTo=2550222122222223Amount = 500 RUB, operationId='1', verificationCode='1234', status=VERIFIED}}";
            Assertions.assertTrue(testStr.equals(transferMoneyRepository.getOperations().toString()));
        } else {
            Assertions.assertDoesNotThrow(() -> transferMoneyRepository.getOperations());
        }
    }

    @Test
    public void test14_SaveVerificationCode_throwsException() {
        Assertions.assertThrows(ErrorOperationId.class, () -> {
            transferMoneyRepository.saveVerificationCode("13", "1313");
        });
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
