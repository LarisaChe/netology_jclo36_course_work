package ru.netology.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.exception.ErrorConfirmation;
import ru.netology.exception.ErrorInputData;
import ru.netology.exception.ErrorOperationId;
import ru.netology.log.Log;
import ru.netology.repository.TransferMoneyRepository;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransferMoneyService {

    Log log = Log.getInstance();

    private TransferMoneyRepository transferMoneyRepository;

    public TransferMoneyService(TransferMoneyRepository transferMoneyRepository) {
        this.transferMoneyRepository = transferMoneyRepository;
    }

    @Getter
    private String currentOperationId;

    public void setCurrentOperationId(String currentOperationId) {
        this.currentOperationId = currentOperationId;
    }

    public String createOperation(TransferMoney transferMoney) throws IOException {
        if (isEmpty(transferMoney)) {
            log.logError("Перевод не был создан: некорректные входные данные. ");
            throw new ErrorInputData("Некорректные входные данные", 0);
        } else {
            log.logInfo(String.format("Запрос на перевод: С карты %s-%s-%s на карту %s суммы %d %s",
                    transferMoney.getCardFromNumber(),
                    transferMoney.getCardFromValidTill(),
                    transferMoney.getCardFromCVV(),
                    transferMoney.getCardToNumber(),
                    transferMoney.getAmount().getValue(),
                    transferMoney.getAmount().getCurrency()));
            setCurrentOperationId(transferMoneyRepository.addOperation(transferMoney));
            return currentOperationId;
        }
    }

    public String confirm(String code) throws ErrorOperationId, IOException {
        log.logInfo("Запрос на подтверждение перевода " + (currentOperationId == null ? "<номер неизвестен>" : currentOperationId));

        if (code == null || code.isEmpty() || currentOperationId == null) {
            log.logError("Некорректный код верификации или номер перевода.");
            throw new ErrorConfirmation("Error confirmation", currentOperationId == null ? 0 : Integer.parseInt(currentOperationId));
        } else {
            transferMoneyRepository.saveVerificationCode(currentOperationId, code);
            return currentOperationId;
        }
    }

    public ConcurrentHashMap<String, TransferMoney> getOperations() {
        return transferMoneyRepository.getOperations();
    }

    public boolean isEmpty(TransferMoney transferMoney) {
        return (transferMoney.getCardFromNumber() == null || transferMoney.getCardFromNumber().isEmpty() ||
                transferMoney.getCardFromValidTill() == null || transferMoney.getCardFromValidTill().isEmpty() ||
                transferMoney.getCardFromCVV() == null || transferMoney.getCardFromCVV().isEmpty() ||
                transferMoney.getCardToNumber() == null || transferMoney.getCardToNumber().isEmpty() ||
                transferMoney.getAmount().getValue() <= 0 ||
                transferMoney.getAmount().getCurrency() == null);
    }

    public int countOperations() {
        return transferMoneyRepository.countOperations();
    }
}
