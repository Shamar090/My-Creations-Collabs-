package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

// Service class implementing AccountService interface
@Service
public class AccountServiceImpl implements AccountService {

    // Data Access Objects for Account, User, and Transfer
    private final AccountDao accountDao;
    private final UserDao userDao;
    private final TransferDao transferDao;

    // Constructor with dependency injection
    @Autowired
    public AccountServiceImpl(AccountDao accountDao, UserDao userDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
    }

    // Retrieves the balance for a given user ID
    @Override
    public BigDecimal getBalance(int userId) {
        Account account = accountDao.getAccountByUserId(userId);
        return account != null ? account.getBalance() : BigDecimal.ZERO;
    }

    // Retrieves a list of all users
    @Override
    public List<User> getAllUsers() {
        return userDao.getUsers();
    }

    // Creates a new transfer
    @Override
    @Transactional
    public Transfer createTransfer(Principal principal, Transfer transfer) {
        // Get sender's user and account information
        User sender = userDao.getUserByUsername(principal.getName());
        Account senderAccount = accountDao.getAccountByUserId(sender.getId());
        Account receiverAccount = accountDao.getAccountByUserId(transfer.getAccountTo());

        // Check if sender has sufficient funds
        if (senderAccount.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        // Calculate new balances
        BigDecimal newSenderBalance = senderAccount.getBalance().subtract(transfer.getAmount());
        BigDecimal newReceiverBalance = receiverAccount.getBalance().add(transfer.getAmount());

        // Update balances in the database
        accountDao.updateBalance(senderAccount.getAccountId(), newSenderBalance);
        accountDao.updateBalance(receiverAccount.getAccountId(), newReceiverBalance);

        // Set transfer details
        transfer.setAccountFrom(senderAccount.getAccountId());
        transfer.setTransferStatusId(2); // Assuming 2 is the ID for "Approved" status
        transfer.setTransferTypeId(2); // Assuming 2 is the ID for "Send" type

        // Create and return the transfer
        return transferDao.createTransfer(transfer);
    }

    // Retrieves a list of pending transfers for the authenticated user
    @Override
    public List<Transfer> getPendingTransfers(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        return transferDao.getPendingTransfers(user.getId());
    }

    // Retrieves a User object by their username
    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}