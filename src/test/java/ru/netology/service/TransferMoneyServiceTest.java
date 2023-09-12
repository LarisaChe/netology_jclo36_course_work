package ru.netology.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.exception.ErrorConfirmation;
import ru.netology.exception.ErrorInputData;
import ru.netology.repository.TransferMoneyRepository;
import ru.netology.transfer.Amount;
import ru.netology.transfer.Currency;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TransferMoneyServiceTest {

    private TransferMoneyService service = new TransferMoneyService(new TransferMoneyRepository());

    private TransferMoney testTransfer = new TransferMoney(
            "1440111456121113",
            "12/29",
            "321",
            "2550222122222223",
            new Amount(500, Currency.RUB));

    @Test
    public void test21_createOperation() throws IOException {
        String testId = service.createOperation(testTransfer);
        Assertions.assertNotNull(testId);
    }

    @Test
    public void test22_confirm() throws IOException {
        String testId = service.confirm("1", "1234");
        Assertions.assertEquals("1", testId);
    }

    @Test
    public void test23_GetOperations() {
        Assertions.assertDoesNotThrow(() -> service.getOperations());
    }

    @ParameterizedTest
    @CsvSource({
            ", 12/29, 321, 2550222122222223, 500, RUB",
            "1440111456121113, , 321, 2550222122222223, 500, RUB",
            "1440111456121113, 12/29, , 2550222122222223, 500, RUB",
            "1440111456121113, 12/29, 321, , 500, RUB",
            "1440111456121113, 12/29, 321, 2550222122222223, 500, ",
            "1440111456121113, 12/29, 321, 2550222122222223, 0, RUB",
            "1440111456121113, 12/29, 321, 2550222122222223, -5, RUB"
    })
    public void test24_createOperation_throwsException(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, int value, Currency currency) {
        TransferMoney testParamTransfer = new TransferMoney(
                cardFromNumber,
                cardFromValidTill,
                cardFromCVV,
                cardToNumber,
                new Amount(value, currency));
        Assertions.assertThrows(ErrorInputData.class, () -> {
            service.createOperation(testParamTransfer);
        });
    }

    @Test
    public void test25_confirm_NullCode_throwsException() {
        Assertions.assertThrows(ErrorConfirmation.class, () -> {
            service.confirm("1", null);
        });
    }

    @Test
    public void test26_confirm_EmptyCode_throwsException() {
        Assertions.assertThrows(ErrorConfirmation.class, () -> {
            service.confirm("1", "");
        });
    }

    @Test
    public void test27_confirm_NullOperationId_throwsException() {
        Assertions.assertThrows(ErrorConfirmation.class, () -> {
            service.confirm(null, "1111");
        });
    }
}
