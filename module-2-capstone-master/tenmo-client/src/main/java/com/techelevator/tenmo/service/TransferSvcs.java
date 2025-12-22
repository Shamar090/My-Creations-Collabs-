package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.dto.TransferDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Interface for transfer-related services in the TEnmo application.
 * This interface defines methods for managing transfers, user information, and account balances.
 */
public interface TransferSvcs {
    /**
     * Sets the authentication token for making authorized API requests.
     * @param token The authentication token to be used for subsequent API calls.
     */
    void setAuthToken(String token);

    /**
     * Retrieves the current balance for the authenticated user.
     * @return The current balance as a BigDecimal.
     */
    BigDecimal getBalance();

    /**
     * Retrieves a list of all users in the system.
     * @return A List of User objects representing all users.
     */
    List<User> getAllUsers();

    /**
     * Creates a new transfer based on the provided transfer data.
     * @param transferDto The transfer data transfer object containing transfer details.
     * @return The created Transfer object.
     */
    Transfer createTransfer(TransferDto transferDto);

    /**
     * Requests a new transfer based on the provided transfer data.
     * @param transferDto The transfer data transfer object containing transfer details.
     * @return The created Transfer object representing the request.
     */
    Transfer requestTransfer(TransferDto transferDto);

    /**
     * Retrieves all transfers associated with the authenticated user.
     * @return A List of Transfer objects for the user.
     */
    List<Transfer> getTransfersForUser();

    Map<Integer, Transfer> getTransferForUser();

    // Retrieves all pending transfers for the authenticated user
    Map<Integer, Transfer> getPendingTransfer();

    /**
     * Retrieves all pending transfers for the authenticated user.
     * @return A List of Transfer objects that are in a pending state.
     */
    List<Transfer> getPendingTransfers();

    /**
     * Updates the status of a specific transfer.
     * @param transferId The ID of the transfer to update.
     * @param statusId The new status ID for the transfer.
     */
    void updateTransferStatus(int transferId, int statusId);

    /**
     * Retrieves the account balance for a specific user.
     * @param userId The ID of the user whose balance is being requested.
     * @return The account balance as a BigDecimal.
     */
    BigDecimal getAccountByUserId(int userId);

    // Retrieves the account balance for a specific user
    int getAccountByAccId(int userId);

    /**
     * Retrieves user information for a specific user ID.
     * @param id The ID of the user to retrieve.
     * @return The User object containing the user's information.
     */
    User getUserByUserId(int id);


    //get username of ID
    String getUserNameByUserId(int id);
}