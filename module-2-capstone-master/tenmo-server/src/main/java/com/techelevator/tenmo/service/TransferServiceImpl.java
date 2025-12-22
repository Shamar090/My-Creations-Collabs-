package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.exception.TransferCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

// Service implementation for handling transfer-related operations
@Service
public class TransferServiceImpl implements TransferService {
    // Logger for this class
    private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    // Data Access Objects for Account, Transfer, and User
    private final AccountDao accountDao;
    private final TransferDao transferDao;
    private final UserDao userDao;

    // Constructor with dependency injection
    public TransferServiceImpl(AccountDao accountDao, TransferDao transferDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    // Get account balance for the authenticated user
    @Override
    public BigDecimal getAccountBalance(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        return accountDao.getBalance(user.getId());
    }

    // Get all users in the system
    @Override
    public List<User> getAllUsers() {
        return userDao.getUsers();
    }

    // Create a new transfer
    @Override
    @Transactional
    public Transfer createTransfer(Principal principal, TransferDto transferDto) {
        try {
            // Get sender and receiver information
            User sender = userDao.getUserByUsername(principal.getName());
            User receiver = userDao.getUserById(transferDto.getReceiverId());

            // Validate sender and receiver
            if (sender == null || receiver == null) {
                throw new IllegalArgumentException("Invalid sender or receiver");
            }

            if (sender.getId() == receiver.getId()) {
                throw new IllegalArgumentException("Cannot transfer to yourself");
            }

            // Get accounts for sender and receiver
            Account senderAccount = accountDao.getAccountByUserId(sender.getId());
            Account receiverAccount = accountDao.getAccountByUserId(receiver.getId());

            // Check if sender has sufficient funds
            if (senderAccount.getBalance().compareTo(transferDto.getAmount()) < 0) {
                throw new IllegalArgumentException("Insufficient funds");
            }

            // Create and set up the transfer
            Transfer transfer = new Transfer();
            transfer.setAccountFrom(senderAccount.getAccountId());
            transfer.setAccountTo(receiverAccount.getAccountId());
            transfer.setAmount(transferDto.getAmount());
            transfer.setTransferTypeId(2); // Assuming 2 is for "Send"
            transfer.setTransferStatusId(2); // Assuming 2 is for "Approved"

            // Create the transfer in the database
            Transfer createdTransfer = transferDao.createTransfer(transfer);

            // Update account balances
            accountDao.updateBalance(sender.getId(), senderAccount.getBalance().subtract(transferDto.getAmount()));
            accountDao.updateBalance(receiver.getId(), receiverAccount.getBalance().add(transferDto.getAmount()));

            return createdTransfer;
        } catch (DataAccessException e) {
            logger.error("Database error while creating transfer", e);
            throw new TransferCreationException("Database error creating transfer: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while creating transfer", e);
            throw new TransferCreationException("Unexpected error creating transfer: " + e.getMessage(), e);
        }
    }

    // Request a new transfer
    @Override
    @Transactional
    public Transfer requestTransfer(Principal principal, TransferDto transferDto) {
        try {
            // Get requester and requestee information
            User requester = userDao.getUserByUsername(principal.getName());
            User requestee = userDao.getUserById(transferDto.getReceiverId());

            // Validate requester and requestee
            if (requester == null || requestee == null) {
                throw new IllegalArgumentException("Invalid requester or requestee");
            }

            if (requester.getId() == requestee.getId()) {
                throw new IllegalArgumentException("Cannot request transfer from yourself");
            }

            // Get accounts for requester and requestee
            Account requesterAccount = accountDao.getAccountByUserId(requester.getId());
            Account requesteeAccount = accountDao.getAccountByUserId(requestee.getId());

            // Create and set up the transfer request
            Transfer transfer = new Transfer();
            transfer.setAccountFrom(requesteeAccount.getAccountId());
            transfer.setAccountTo(requesterAccount.getAccountId());
            transfer.setAmount(transferDto.getAmount());
            transfer.setTransferTypeId(1); // Assuming 1 is for "Request"
            transfer.setTransferStatusId(1); // Assuming 1 is for "Pending"

            return transferDao.createTransfer(transfer);
        } catch (DataAccessException e) {
            logger.error("Database error while requesting transfer", e);
            throw new TransferCreationException("Database error requesting transfer: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while requesting transfer", e);
            throw new TransferCreationException("Unexpected error requesting transfer: " + e.getMessage(), e);
        }
    }

    // Get pending transfers for the authenticated user
    @Override
    public List<Transfer> getPendingTransfers(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        return transferDao.getPendingTransfers(user.getId());
    }

    // Update the status of a transfer
    @Override
    @Transactional
    public void updateTransferStatus(Principal principal, int transferId, int statusId) {
        // Get current user and transfer information
        User currentUser = userDao.getUserByUsername(principal.getName());
        Transfer transfer = transferDao.getTransferById(transferId);

        // Validate transfer
        if (transfer == null) {
            throw new IllegalArgumentException("Transfer not found");
        }

        // Check if the current user is authorized to update this transfer
        Account userAccount = accountDao.getAccountByUserId(currentUser.getId());

        System.out.println(transfer.getTransferId()+" "+transfer.getAccountFrom()+" "+transfer.getAccountTo()+" "+transfer.getTransferStatusId());
        System.out.println(userAccount.getAccountId());
        if (transfer.getAccountFrom() != userAccount.getAccountId()) {
            throw new IllegalArgumentException("Unauthorized action: You can only approve/reject transfers sent to you");
        }

        // Check if the transfer is in a state that can be updated
        if (transfer.getTransferStatusId() != 1) { // Assuming 1 is the ID for "Pending" status
            throw new IllegalArgumentException("Only pending transfers can be updated");
        }

        // If approving the transfer, check and update balances
        if (statusId == 2) { // Assuming 2 is the ID for "Approved" status
            Account senderAccount = accountDao.getAccountById(transfer.getAccountFrom());
            Account receiverAccount = accountDao.getAccountById(transfer.getAccountTo());
            System.out.println("IT reach here"+senderAccount.getAccountId()+" "+ receiverAccount.getAccountId());
            if (senderAccount.getBalance().compareTo(transfer.getAmount()) < 0) {
                throw new IllegalArgumentException("Insufficient funds to approve transfer");
            }

            // Update account balances
            accountDao.updateBalance(senderAccount.getUserId(), senderAccount.getBalance().subtract(transfer.getAmount()));
            accountDao.updateBalance(receiverAccount.getUserId(), receiverAccount.getBalance().add(transfer.getAmount()));
        }

        // Update the transfer status
        transferDao.updateTransferStatus(transferId, statusId);
    }

    // Get all transfers for the authenticated user
    @Override
    public List<Transfer> getTransfersForUser(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        return transferDao.getTransfersForUser(user.getId());
    }
}
