package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

// Interface defining operations for Account data access
public interface AccountDao {
    // Retrieves an Account object associated with a given user ID
    Account getAccountByUserId(int userId);

    // Gets the balance of an account associated with a given user ID
    BigDecimal getBalance(int userId);

    // Updates the balance of an account associated with a given user ID
    void updateBalance(int userId, BigDecimal newBalance);

    // Retrieves an Account object by its account ID
    Account getAccountById(int accountId);
}