package ru.netology.controller;

public class ResponseTransfer {
    private final String operationId;

    public ResponseTransfer(String operationId) {
        this.operationId = operationId;
    }

    public String getOperationId() {
        return operationId;
    }
}
