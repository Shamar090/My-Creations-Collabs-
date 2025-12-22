package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;

import java.math.BigDecimal;

/**
 * Interface defining account-related services for the TEnmo application.
 * This interface outlines methods for account management and balance retrieval.
 */
public interface AccountSvcs {

    /**
     * Retrieves an account by its ID.
     *
     * @param accountId The ID of the account to retrieve.
     * @return The Account object if found, or null if not found.
     */
    Account getAccount(int accountId);

    /**
     * Retrieves the balance for an authenticated user's account.
     *
     * @param user The authenticated user whose balance is being requested.
     * @return The account balance as a BigDecimal.
     */
    BigDecimal getBalance(AuthenticatedUser user);

    /**
     * Updates an existing account with new information.
     *
     * @param newAccount The Account object containing updated information.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateAccount(Account newAccount);

    /**
     * Creates a new account.
     *
     * @param newAccount The Account object containing information for the new account.
     * @return The created Account object, or null if creation failed.
     */
    Account createAccount(Account newAccount);

    /**
     * Sets the authentication token for making authorized API requests.
     *
     * @param authToken The authentication token to be used for subsequent API calls.
     */
    void setAuthToken(String authToken);
}