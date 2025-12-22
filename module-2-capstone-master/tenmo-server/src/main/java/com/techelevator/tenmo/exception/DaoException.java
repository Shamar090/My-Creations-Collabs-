package com.techelevator.tenmo.exception;

// Custom exception class for Data Access Object (DAO) related exceptions
public class DaoException extends RuntimeException {
    // Default constructor
    public DaoException() {
        super();
    }

    // Constructor with a custom error message
    public DaoException(String message) {
        super(message);
    }

    // Constructor with a custom error message and a cause (another exception)
    public DaoException(String message, Exception cause) {
        super(message, cause);
    }
}