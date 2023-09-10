package ru.netology.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import ru.netology.exception.ErrorVerificationCode;

import java.util.Random;

public class TransferMoney {

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 9;
    private static final int lenVerificationCode = 12;

    @NotBlank
    @Size(min = 16, max = 20)
    private String cardFromNumber;

    @NotBlank
    @Size(min = 5)
    private String cardFromValidTill;

    @NotBlank
    @Size(min = 3)
    private String cardFromCVV;

    @NotBlank
    @Size(min = 16, max = 20)
    private String cardToNumber;

    @JsonProperty("amount")
    private Amount amount;

    private String operationId;
    private String verificationCode;
    private Status status;

    public TransferMoney() {
    }

    public TransferMoney(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
        this.status = Status.CREATED;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void generateVerificationCode() throws ErrorVerificationCode {
        if (verificationCode == null || verificationCode == "") {
            verificationCode = generateNumberString();
            status = Status.VERIFIED;
        } else throw new ErrorVerificationCode("Verification Code is already existed");
    }

    private String generateNumberString() {
        StringBuilder vc = new StringBuilder();
        Random random = new Random();
        for (int i = 1; i <= lenVerificationCode; i++) {
            vc.append(random.nextInt(MAX_VALUE - MIN_VALUE + 1));
        }
        return vc.toString();
    }

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TransferMoney{" +
                "cardFrom=" +
                cardFromNumber + " / " +
                cardFromValidTill + " / " +
                cardFromCVV +
                ", cardTo=" + cardToNumber +
                amount +
                ", operationId='" + operationId + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", status=" + status +
                '}';
    }
}

