package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;

/**
 * Interface defining authentication services for the TEnmo application.
 * This interface outlines methods for user login and registration.
 */
public interface AuthenticationSvcs {

    /**
     * Attempts to log in a user with the provided credentials.
     *
     * @param credentials The user's login credentials (username and password).
     * @return An AuthenticatedUser object if login is successful, or null if it fails.
     */
    AuthenticatedUser login(UserCredentials credentials);

    /**
     * Attempts to register a new user with the provided credentials.
     *
     * @param credentials The new user's registration credentials (username and password).
     * @return true if registration is successful, false otherwise.
     */
    boolean register(UserCredentials credentials);
}