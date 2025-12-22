package com.techelevator.tenmo.exception;

// Custom exception class for situations where there are insufficient funds
public class InsufficientFundsException extends RuntimeException {
    // Constructor with a custom error message
    public InsufficientFundsException(String message) {
        super(message);
    }
}