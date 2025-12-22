package com.techelevator.tenmo.exception;

// Custom exception class for situations where a transfer is not found
public class TransferNotFoundException extends RuntimeException {
    // Constructor with a custom error message
    public TransferNotFoundException(String message) {
        super(message);
    }
}