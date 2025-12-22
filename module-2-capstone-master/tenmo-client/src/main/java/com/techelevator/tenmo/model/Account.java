package com.techelevator.tenmo.model;

import java.math.BigDecimal;

/**
 * Represents a user's account in the TEnmo application.
 * This class holds information about an account, including its ID, balance, and associated user ID.
 */
public class Account {
    // Unique identifier for the account
    private int accountId;
    // Current balance of the account
    private BigDecimal balance;
    // ID of the user who owns this account
    private int userId;

    /**
     * Default constructor for Account.
     */
    public Account() {}

    /**
     * Parameterized constructor to create an Account with all properties.
     * @param accountId The unique identifier for the account.
     * @param balance The initial balance of the account.
     * @param userId The ID of the user who owns this account.
     */
    public Account(int accountId, BigDecimal balance, int userId) {
        this.accountId = accountId;
        this.balance = balance;
        this.userId = userId;
    }

    /**
     * Gets the account ID.
     * @return The account ID.
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Sets the account ID.
     * @param accountId The account ID to set.
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets the current balance of the account.
     * @return The current balance.
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the account.
     * @param balance The balance to set.
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Gets the ID of the user who owns this account.
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who owns this account.
     * @param userId The user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns a string representation of the Account object.
     * @return A string containing the account's properties.
     */
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", userId=" + userId +
                '}';
    }
}