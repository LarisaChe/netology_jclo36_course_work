package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.ErrorOperationId;
import ru.netology.exception.ErrorVerificationCode;
import ru.netology.log.Log;
import ru.netology.transfer.Status;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferMoneyRepository {

    Log log = Log.getInstance();
    private static AtomicInteger num = new AtomicInteger(0);

    public TransferMoneyRepository() {
    }

    private static ConcurrentHashMap<String, TransferMoney> operationsTransferMoney = new ConcurrentHashMap<>();

    public String addOperation(TransferMoney transferMoney) throws ErrorOperationId, IOException {
        num.incrementAndGet();
        String operationId = num.toString();
        if (!operationsTransferMoney.containsKey(operationId)) {
            transferMoney.setOperationId(operationId);
            transferMoney.setStatus(Status.CREATED);
            operationsTransferMoney.put(operationId, transferMoney);
        }
        else {
            log.logError("Уже существует перевод с номером " + operationId);
            throw new ErrorOperationId("Error operation ID");
        }
        log.logInfo(String.format("Перевод зарегистирован под номером %s. Статус перевода: %s", operationId, transferMoney.getStatus()));
        return operationId;
    }

    public TransferMoney getOperation(String operationId) {
        return operationsTransferMoney.getOrDefault(operationId, null);
    }

    public String verifyOperation(String operationId) throws ErrorOperationId, ErrorVerificationCode, IOException {
        if (operationsTransferMoney.containsKey(operationId)) {
            System.out.println("найдена: " + operationId);
            log.logInfo("Найден перевод с номером " + operationId);
        }
        TransferMoney transferMoney = operationsTransferMoney.getOrDefault(operationId, null);
        printOperations();
        System.out.println(operationId + " -1- " + transferMoney);
        if (transferMoney != null) {
            transferMoney.generateVerificationCode();
            System.out.println(" -2- " + transferMoney);
            operationsTransferMoney.put(operationId, transferMoney);
            log.logInfo(String.format("Перевод %s подтвержден. Код верификации: %s. Статус перевода: %s", operationId, transferMoney.getVerificationCode(), transferMoney.getStatus()));
            return transferMoney.getVerificationCode();
        }
        else {
            log.logError("Не найден перевод с номером " + operationId);
            throw new ErrorOperationId("Error operation ID");
        }
    }

    public void printOperations() throws IOException {
        System.out.println(operationsTransferMoney);
        log.logInfo(operationsTransferMoney.toString());
    }

    public ConcurrentHashMap<String, TransferMoney> getOperations() {
        return operationsTransferMoney;
    }
}
