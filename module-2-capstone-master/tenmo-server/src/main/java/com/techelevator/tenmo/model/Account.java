package com.techelevator.tenmo.model;

import java.math.BigDecimal;

// This class represents an Account in the tenmo application
public class Account {
    // Unique identifier for the account
    private int accountId;

    // User ID associated with this account
    private int userId;

    // Current balance of the account, using BigDecimal for precise calculations
    private BigDecimal balance;

    // Getter method for accountId
    public int getAccountId() {
        return accountId;
    }

    // Setter method for accountId
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    // Getter method for userId
    public int getUserId() {
        return userId;
    }

    // Setter method for userId
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter method for balance
    public BigDecimal getBalance() {
        return balance;
    }

    // Setter method for balance
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}