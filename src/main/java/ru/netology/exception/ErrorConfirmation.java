package ru.netology.exception;

public class ErrorConfirmation extends RuntimeException {
    private int id;
    public ErrorConfirmation(String msg, int id) {
        super(msg);
        this.id = id;
    }
    public int getOperationId() {
        return id;
    }
}
