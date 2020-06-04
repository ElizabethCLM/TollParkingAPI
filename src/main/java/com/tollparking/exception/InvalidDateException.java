package com.tollparking.exception;

/**
 * Exception thrown when invalid dates are passed to the billing.
 * The dates are invalid when they are null or when the start date is after the end date
 */
public class InvalidDateException extends RuntimeException {

    public InvalidDateException(String message) {
        super(message);
    }
}
