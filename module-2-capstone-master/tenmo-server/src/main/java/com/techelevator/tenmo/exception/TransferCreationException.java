package com.techelevator.tenmo.exception;

// Custom exception class for errors occurring during transfer creation
public class TransferCreationException extends RuntimeException {
    // Constructor with a custom error message
    public TransferCreationException(String message) {
        super(message);
    }

    // Constructor with a custom error message and a cause (another exception)
    public TransferCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}