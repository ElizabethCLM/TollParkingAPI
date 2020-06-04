package com.tollparking.exception;

/**
 * Exception thrown when a negative amount is set to a pricing policy
 */
public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException(String message) {
        super(message);
    }
}
