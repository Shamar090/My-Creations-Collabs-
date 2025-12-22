package com.techelevator.tenmo.exception;

// Custom exception class for situations where a user is not found
public class UserNotFoundException extends RuntimeException {
    // Constructor with a custom error message
    public UserNotFoundException(String message) {
        super(message);
    }
}