package com.tollparking.exception;

/**
 * Exception thrown when a ticket is not found in the parking
 */
public class TicketNotFoundException extends Exception{

    public  TicketNotFoundException(String message) {
        super(message);
    }
}
