package com.techelevator.tenmo.dto;

import java.math.BigDecimal;

// This class represents a Data Transfer Object (DTO) for a transfer transaction
public class TransferDto {

    // The ID of the receiver of the transfer
    private int receiverId;
    // The amount of the transfer
    private BigDecimal amount;
    // The type of transfer (e.g., "Send" or "Request")
    private String type;

    // Default constructor
    public TransferDto() {}

    // Constructor with parameters
    public TransferDto(int receiverId, BigDecimal amount, String type) {
        this.receiverId = receiverId;
        this.amount = amount;
        this.type = type;
    }

    // Getters and setters
    // Get the receiver's ID
    public int getReceiverId() {
        return receiverId;
    }

    // Set the receiver's ID
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    // Get the transfer amount
    public BigDecimal getAmount() {
        return amount;
    }

    // Set the transfer amount
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Get the transfer type
    public String getType() {
        return type;
    }

    // Set the transfer type
    public void setType(String type) {
        this.type = type;
    }

    // Override toString method for object representation
    @Override
    public String toString() {
        return "TransferDto{" +
                "receiverId=" + receiverId +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                '}';
    }
}