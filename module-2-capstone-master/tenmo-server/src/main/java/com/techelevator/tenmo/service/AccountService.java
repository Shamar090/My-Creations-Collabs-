package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

// Interface defining the contract for account-related operations in the tenmo application
public interface AccountService {
    // Retrieves the balance for a given user ID
    BigDecimal getBalance(int userId);

    // Retrieves a list of all users in the system
    List<User> getAllUsers();

    // Creates a new transfer, using the principal for authentication and the transfer object for details
    Transfer createTransfer(Principal principal, Transfer transfer);

    // Retrieves a list of pending transfers for the authenticated user
    List<Transfer> getPendingTransfers(Principal principal);

    // Retrieves a User object by their username
    User getUserByUsername(String username);
}