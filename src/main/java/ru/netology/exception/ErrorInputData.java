package ru.netology.exception;

public class ErrorInputData extends RuntimeException {
    private int id;
    public ErrorInputData(String msg, int id) {
        super(msg);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
