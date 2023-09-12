package ru.netology.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.netology.exception.ErrorConfirmation;
import ru.netology.exception.ErrorInputData;
import ru.netology.repository.TransferMoneyRepository;
import ru.netology.service.TransferMoneyService;
import ru.netology.transfer.Amount;
import ru.netology.transfer.Code;
import ru.netology.transfer.TransferMoney;


@TestMethodOrder(MethodOrderer.MethodName.class)
public class TransferMoneyControllerTest {

    TransferMoneyController controller = new TransferMoneyController(new TransferMoneyService(new TransferMoneyRepository()));

    @Test
    public void test01_createOperation() {
        Assertions.assertThrows(ErrorInputData.class, () -> {
            controller.transfer(new TransferMoney(null, null, null, null, new Amount(0, null)));
        });
    }

    @Test
    public void test02_confirm_throwsException() {
        Assertions.assertThrows(ErrorConfirmation.class, () -> {
            controller.confirmOperation(new Code("1313", "123455677"));
        });
    }

    @Test
    public void test03_GetOperations() {
        Assertions.assertDoesNotThrow(() -> controller.getOperations());
    }
}
