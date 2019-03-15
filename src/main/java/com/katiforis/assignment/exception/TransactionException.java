package com.katiforis.assignment.exception;

/**
 * In case of any exception during communication with the bank.
 */
public class TransactionException extends Exception {
    public TransactionException(String message) {
        super(message);
    }
}
