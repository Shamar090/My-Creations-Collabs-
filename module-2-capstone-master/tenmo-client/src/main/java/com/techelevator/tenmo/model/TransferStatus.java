package com.techelevator.tenmo.model;

/**
 * Enumeration representing the possible statuses of a transfer in the TEnmo application.
 */
public enum TransferStatus {
    // Transfer is awaiting approval
    PENDING(1),
    // Transfer has been approved and completed
    APPROVED(2),
    // Transfer has been rejected and will not be completed
    REJECTED(3);

    // The numeric ID associated with each status
    private final int id;

    /**
     * Constructor for TransferStatus enum.
     * @param id The numeric ID associated with the status.
     */
    TransferStatus(int id) {
        this.id = id;
    }

    /**
     * Retrieves the numeric ID of the transfer status.
     * @return The ID of the transfer status.
     */
    public int getId() {
        return id;
    }
}