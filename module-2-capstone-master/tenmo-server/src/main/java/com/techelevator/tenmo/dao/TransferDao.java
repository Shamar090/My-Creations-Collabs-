package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

// Interface defining operations for Transfer data access
public interface TransferDao {
    // Creates a new transfer in the database
    Transfer createTransfer(Transfer transfer);

    // Retrieves a transfer by its ID
    Transfer getTransferById(int transferId);

    // Retrieves all pending transfers for a specific user
    List<Transfer> getPendingTransfers(int userId);

    // Updates the status of a transfer
    void updateTransferStatus(int transferId, int statusId);

    // Retrieves all transfers for a specific user
    List<Transfer> getTransfersForUser(int userId);
}