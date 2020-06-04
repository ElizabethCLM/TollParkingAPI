package com.tollparking.exception;

/**
 * Exception thrown when the number of slots in a parking is negative
 */
public class InvalidCapacityException extends Exception {

    public InvalidCapacityException(String message) {
        super(message);
    }

}
