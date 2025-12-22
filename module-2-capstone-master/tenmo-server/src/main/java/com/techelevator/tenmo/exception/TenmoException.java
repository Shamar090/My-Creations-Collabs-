package com.techelevator.tenmo.exception;

// Custom exception class for general Tenmo application errors
public class TenmoException extends RuntimeException {
    // Constructor with a custom error message
    public TenmoException(String message) {
        super(message);
    }

    // Constructor with a custom error message and a cause (another exception)
    public TenmoException(String message, Throwable cause) {
        super(message, cause);
    }
}