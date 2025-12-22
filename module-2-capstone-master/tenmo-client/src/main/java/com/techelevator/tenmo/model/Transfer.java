package com.techelevator.tenmo.model;

import java.math.BigDecimal;

/**
 * Represents a transfer of funds in the TEnmo application.
 */
public class Transfer {
    // Unique identifier for the transfer
    private int transferId;
    // ID of the user sending the funds
    private int senderId;
    // ID of the user receiving the funds
    private int receiverId;
    // Amount of money being transferred
    private BigDecimal amount;
    // Type of transfer (e.g., "Send" or "Request")
    private String type;
    // ID representing the status of the transfer (e.g., pending, approved, rejected)
    private int transferStatusId;
    // ID representing the type of transfer
    private int transferTypeId;
    // Account ID from which the funds are being transferred
    private int accountFrom;
    // Account ID to which the funds are being transferred
    private int accountTo;

    // Default constructor
    public Transfer() {}

    /**
     * Parameterized constructor to create a Transfer object with all properties.
     */
    public Transfer(int transferId, int senderId, int receiverId, BigDecimal amount, String type, int transferStatusId, int transferTypeId, int accountFrom, int accountTo) {
        this.transferId = transferId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.type = type;
        this.transferStatusId = transferStatusId;
        this.transferTypeId = transferTypeId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }

    // Getters and Setters for all properties
    public int getTransferId() { return transferId; }
    public void setTransferId(int transferId) { this.transferId = transferId; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public int getReceiverId() { return receiverId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getTransferStatusId() { return transferStatusId; }
    public void setTransferStatusId(int transferStatusId) { this.transferStatusId = transferStatusId; }

    public int getTransferTypeId() { return transferTypeId; }
    public void setTransferTypeId(int transferTypeId) { this.transferTypeId = transferTypeId; }

    public int getAccountFrom() { return accountFrom; }
    public void setAccountFrom(int accountFrom) { this.accountFrom = accountFrom; }

    public int getAccountTo() { return accountTo; }
    public void setAccountTo(int accountTo) { this.accountTo = accountTo; }

    /**
     * Returns a string representation of the Transfer object.
     */
    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", transferStatusId=" + transferStatusId +
                ", transferTypeId=" + transferTypeId +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                '}';
    }
}