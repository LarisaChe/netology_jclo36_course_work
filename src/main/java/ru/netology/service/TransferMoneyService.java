package ru.netology.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.exception.ErrorConfirmation;
import ru.netology.exception.ErrorInputData;
import ru.netology.exception.ErrorOperationId;
import ru.netology.exception.ErrorVerificationCode;
import ru.netology.log.Log;
import ru.netology.repository.TransferMoneyRepository;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransferMoneyService {

    Log log = Log.getInstance();
    @Autowired
    private TransferMoneyRepository transferMoneyRepository;

    public TransferMoneyService(TransferMoneyRepository transferMoneyRepository) {
        this.transferMoneyRepository = transferMoneyRepository;
    }

    public String createOperation(TransferMoney transferMoney) throws ErrorOperationId, IOException {
        log.logInfo(String.format("Запрос на перевод: С карты %s-%s-%s на карту %s суммы %d %s",
                transferMoney.getCardFromNumber(),
                transferMoney.getCardFromValidTill(),
                transferMoney.getCardFromCVV(),
                transferMoney.getCardToNumber(),
                //transferMoney.getAmountValue(),
                //transferMoney.getAmountCurrency()));
                transferMoney.getAmount()));
        if (isEmpty(transferMoney)) {
            log.logError("Перевод не был создан.");
            throw new ErrorInputData("dddd!!!", "0");
        } else {
            return transferMoneyRepository.addOperation(transferMoney);
        }
    }

    public String confirm(String operationId) throws ErrorOperationId, ErrorVerificationCode, IOException {
        log.logInfo("Запрос на подтверждение перевода " + operationId);
        String verificationCode = transferMoneyRepository.verifyOperation(operationId);
        if (verificationCode == null) {
            log.logError("Не был сгенерирован код верификации для перевода " + operationId);
            throw new ErrorConfirmation("qqqqqq", operationId);
        }
        else {
            return verificationCode;
        }
    }

    public ConcurrentHashMap<String, TransferMoney> getOperations() {
        return transferMoneyRepository.getOperations();
    }

    public boolean isEmpty(TransferMoney transferMoney) {
            return (transferMoney.getCardFromNumber() != null &&
                    transferMoney.getCardFromValidTill() != null &&
                    transferMoney.getCardFromCVV() != null &&
                    transferMoney.getCardToNumber() != null &&
                    transferMoney.getAmount().getValue() < 1 &&
                    transferMoney.getAmount().getCurrency() != null);
    }
}
