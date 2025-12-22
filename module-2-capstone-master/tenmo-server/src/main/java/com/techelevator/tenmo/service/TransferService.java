package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.dto.TransferDto;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

// Interface defining the contract for transfer-related operations in the tenmo application
public interface TransferService {
    // Retrieves the account balance for the authenticated user
    BigDecimal getAccountBalance(Principal principal);

    // Retrieves a list of all users in the system
    List<User> getAllUsers();

    // Creates a new transfer initiated by the authenticated user
    Transfer createTransfer(Principal principal, TransferDto transferDto);

    // Requests a new transfer to be initiated by another user
    Transfer requestTransfer(Principal principal, TransferDto transferDto);

    // Retrieves a list of pending transfers for the authenticated user
    List<Transfer> getPendingTransfers(Principal principal);

    // Updates the status of a transfer
    void updateTransferStatus(Principal principal, int transferId, int statusId);

    // Retrieves a list of all transfers associated with the authenticated user
    List<Transfer> getTransfersForUser(Principal principal);
}
