package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.ErrorConfirmation;
import ru.netology.exception.ErrorTransfer;
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

    public String addOperation(TransferMoney transferMoney) throws IOException {
        num.incrementAndGet();
        String operationId = num.toString();

        transferMoney.setOperationId(operationId);
        transferMoney.setStatus(Status.CREATED);

        if (operationsTransferMoney.containsKey(operationId)) {
            log.logError(String.format("Перевод %s не был создан ", operationId));
            throw new ErrorTransfer("Error creating transfer", 0);
        }
        operationsTransferMoney.put(operationId, transferMoney);
        log.logInfo(String.format("Перевод зарегистрирован под номером %s. Статус перевода: %s", operationId, transferMoney.getStatus()));
        return operationId;
    }

    public String saveVerificationCode(String operationId, String verificationCode) throws IOException {
        if (operationsTransferMoney.containsKey(operationId)) {
            log.logInfo("Найден перевод с номером " + operationId);
            TransferMoney transferMoney = operationsTransferMoney.get(operationId);
            transferMoney.setVerificationCode(verificationCode);
            transferMoney.setStatus(Status.VERIFIED);
            operationsTransferMoney.put(operationId, transferMoney);
            log.logInfo(String.format("Перевод %s подтвержден. Код верификации: %s. Статус перевода: %s", operationId, transferMoney.getVerificationCode(), transferMoney.getStatus()));
            return transferMoney.getOperationId();
        } else {
            log.logError("Не найден перевод с номером " + operationId);
            throw new ErrorConfirmation("Error operation ID: "+ operationId, Integer.parseInt(operationId));
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
