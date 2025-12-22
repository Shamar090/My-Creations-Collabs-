package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.User;
import java.util.List;

/**
 * Interface for user-related services in the TEnmo application.
 * This interface defines methods for retrieving user information and managing authentication.
 */
public interface UserSvcs {

    /**
     * Retrieves a list of all users in the system.
     *
     * @return A List of User objects representing all users in the system.
     */
    List<User> getAllUsers();

    /**
     * Sets the authentication token for making authorized API requests.
     *
     * @param authToken The authentication token to be used for subsequent API calls.
     */
    void setAuthToken(String authToken);
}