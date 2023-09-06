package ru.netology.exception;

public class ErrorConfirmation extends RuntimeException {
    private String operationId;
    public ErrorConfirmation(String msg, String operationId) {
        super(msg);
        this.operationId = operationId;
    }
    public String getOperationId() {
        return operationId;
    }
}
