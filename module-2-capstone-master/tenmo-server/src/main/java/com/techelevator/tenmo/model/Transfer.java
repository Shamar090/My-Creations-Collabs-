package com.techelevator.tenmo.model;

import java.math.BigDecimal;

// This class represents a Transfer in the tenmo application
public class Transfer {
    // Unique identifier for the transfer
    private int transferId;
    // ID of the user sending the transfer
    private int senderId;
    // ID of the user receiving the transfer
    private int receiverId;
    // Amount of the transfer
    private BigDecimal amount;
    // Type of the transfer
    private String type;
    // ID representing the status of the transfer
    private int transferStatusId;
    // ID representing the type of the transfer
    private int transferTypeId;
    // Account ID from which the transfer is sent
    private int accountFrom;
    // Account ID to which the transfer is sent
    private int accountTo;

    // Default constructor
    public Transfer() {
    }

    // Constructor with essential transfer details
    public Transfer(int senderId, int receiverId, BigDecimal amount, String type) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.type = type;
    }

    // Getters and setters
    // Getter for transferId
    public int getTransferId() {
        return transferId;
    }

    // Setter for transferId
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    // Getter for senderId
    public int getSenderId() {
        return senderId;
    }

    // Setter for senderId
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    // Getter for receiverId
    public int getReceiverId() {
        return receiverId;
    }

    // Setter for receiverId
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    // Getter for amount
    public BigDecimal getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Getter for type
    public String getType() {
        return type;
    }

    // Setter for type
    public void setType(String type) {
        this.type = type;
    }

    // Getter for transferStatusId
    public int getTransferStatusId() {
        return transferStatusId;
    }

    // Setter for transferStatusId
    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    // Getter for transferTypeId
    public int getTransferTypeId() {
        return transferTypeId;
    }

    // Setter for transferTypeId
    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    // Getter for accountFrom
    public int getAccountFrom() {
        return accountFrom;
    }

    // Setter for accountFrom
    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    // Getter for accountTo
    public int getAccountTo() {
        return accountTo;
    }

    // Setter for accountTo
    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }
}
