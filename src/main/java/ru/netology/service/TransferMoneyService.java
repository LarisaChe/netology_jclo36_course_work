package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.ErrorConfirmation;
import ru.netology.exception.ErrorInputData;
import ru.netology.log.Log;
import ru.netology.repository.TransferMoneyRepository;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransferMoneyService {

    Log log = Log.getInstance();

    private final TransferMoneyRepository transferMoneyRepository;

    public TransferMoneyService(TransferMoneyRepository transferMoneyRepository) {
        this.transferMoneyRepository = transferMoneyRepository;
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
            return transferMoneyRepository.addOperation(transferMoney);
        }
    }

    public String confirm(String operationId, String code) throws IOException {
        log.logInfo("Запрос на подтверждение перевода " + (operationId == null ? "<номер неизвестен>" : operationId));

        if (code == null || code.isEmpty() || operationId == null) {
            log.logError("Некорректный код верификации или номер перевода.");
            throw new ErrorConfirmation("Error confirmation", operationId == null ? 0 : Integer.parseInt(operationId));
        } else {
            transferMoneyRepository.saveVerificationCode(operationId, code);
            return operationId;
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

}
