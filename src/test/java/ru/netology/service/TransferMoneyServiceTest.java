package ru.netology.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.exception.ErrorConfirmation;
import ru.netology.exception.ErrorInputData;
import ru.netology.exception.ErrorOperationId;
import ru.netology.repository.TransferMoneyRepository;
import ru.netology.transfer.Amount;
import ru.netology.transfer.Currency;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TransferMoneyServiceTest {

    private TransferMoneyService service = new TransferMoneyService(new TransferMoneyRepository());

    private String testId = "2";

    public TransferMoney testTransfer = new TransferMoney(
            "1440111456121113",
            "12/29",
            "321",
            "2550222122222223",
            new Amount(500, Currency.RUB));

    public void setTestId(String testId) {
        this.testId = testId;
    }

    @Test
    public void test20() {
        int n = service.countOperations() + 1;
        setTestId(String.valueOf(n + 1));
    }

    @Test
    public void test21_createOperation() throws IOException {
        String operationId = service.createOperation(testTransfer);
        Assertions.assertEquals(testId, operationId);
    }

    @Test
    public void test22_confirm() throws ErrorOperationId, IOException {
        service.setCurrentOperationId(testId);
        String operationId = service.confirm("1234");
        Assertions.assertEquals(testId, operationId);
    }

    @Test
    public void test23_GetOperations() {
        if (testId.equals("1")) {
            String testStr = "{1=TransferMoney{cardFrom=1440111456121113 / 12/29 / 321, cardTo=2550222122222223Amount = 500 RUB, operationId='1', verificationCode='1234', status=VERIFIED}}";
            Assertions.assertTrue(testStr.equals(service.getOperations().toString()));
        }
        else {
            Assertions.assertDoesNotThrow(() -> service.getOperations());
        }
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
        service.setCurrentOperationId(testId);
        Assertions.assertThrows(ErrorConfirmation.class,() -> {
            service.confirm(null);
        });
    }

    @Test
    public void test26_confirm_EmptyCode_throwsException() {
        service.setCurrentOperationId(testId);
        Assertions.assertThrows(ErrorConfirmation.class,() -> {
            service.confirm("");
        });
    }
}
