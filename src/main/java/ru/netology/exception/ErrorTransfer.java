package ru.netology.exception;

public class ErrorTransfer extends RuntimeException {
    private int id;
    public ErrorTransfer(String msg, int id) {
        super(msg);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
