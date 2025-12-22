package com.techelevator.tenmo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

// Data Transfer Object for Transfer operations
public class TransferDto {

    // Receiver's ID, must not be null and must be a positive integer
    @NotNull(message = "Receiver ID is required")
    @Min(value = 1, message = "Receiver ID must be a positive integer")
    private int receiverId;

    // Transfer amount, must not be null and must be positive
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    // Transfer type, must be either 'Send' or 'Request'
    @NotNull(message = "Transfer type is required")
    @Pattern(regexp = "^(Send|Request)$", message = "Transfer type must be either 'Send' or 'Request'")
    private String type;

    // Default no-args constructor
    public TransferDto() {
    }

    // Parameterized constructor
    public TransferDto(int receiverId, BigDecimal amount, String type) {
        this.receiverId = receiverId;
        this.amount = amount;
        this.type = type;
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