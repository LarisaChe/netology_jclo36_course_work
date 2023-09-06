package ru.netology.exception;

public class ErrorInputData extends RuntimeException {
    private String operationId;
    public ErrorInputData(String msg, String operationId) {
        super(msg);
        this.operationId = operationId;
    }

    public String getOperationId() {
        return operationId;
    }
}
