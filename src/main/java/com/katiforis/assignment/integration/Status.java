package com.katiforis.assignment.integration;

/**
 * Represents the status of transaction with the bank
 */
public class Status {

    public static final int SUCCESS = 0;
    public static final int INSUFFICIENT_BALANCE = 1;
    public static final int OTHER_FAILURE = 2;

    int code;
    String message;

    public Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
