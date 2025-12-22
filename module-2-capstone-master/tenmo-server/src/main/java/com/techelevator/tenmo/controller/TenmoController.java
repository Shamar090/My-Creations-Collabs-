package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

// REST controller for Tenmo application operations
@RestController
@PreAuthorize("isAuthenticated()") // Ensures all endpoints require authentication
public class TenmoController {

    private static final Logger logger = LoggerFactory.getLogger(TenmoController.class);
    private final TransferService transferService;
    private final AccountDao accountDao;
    private final UserDao userDao;

    // Constructor injection of dependencies
    public TenmoController(TransferService transferService, AccountDao accountDao, UserDao userDao) {
        this.transferService = transferService;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    // Endpoint to get the balance of the authenticated user's account
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(Principal principal) {
        try {
            BigDecimal balance = transferService.getAccountBalance(principal);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            logger.error("Error retrieving balance", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = transferService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error retrieving users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to create a new transfer
    @PostMapping("/transfers")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTransfer(@RequestBody TransferDto transferDto, Principal principal) {
        try {
            Transfer createdTransfer = transferService.createTransfer(principal, transferDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating transfer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // Endpoint to request a new transfer
    @PostMapping("/transfers/request")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> requestTransfer(@RequestBody TransferDto transferDto, Principal principal) {
        try {
            Transfer createdTransfer = transferService.requestTransfer(principal, transferDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error requesting transfer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // Endpoint to get all transfers for the authenticated user
    @GetMapping("/transfers")
    public ResponseEntity<List<Transfer>> getTransfersForUser(Principal principal) {
        try {
            List<Transfer> transfers = transferService.getTransfersForUser(principal);
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            logger.error("Error retrieving transfers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to get pending transfers for the authenticated user
    @GetMapping("/transfers/pending")
    public ResponseEntity<List<Transfer>> getPendingTransfers(Principal principal) {
        try {
            List<Transfer> pendingTransfers = transferService.getPendingTransfers(principal);
            return ResponseEntity.ok(pendingTransfers);
        } catch (Exception e) {
            logger.error("Error retrieving pending transfers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to update the status of a transfer
    @PutMapping("/transfers/{id}")
    public ResponseEntity<?> updateTransferStatus(@PathVariable int id, @RequestParam int statusId, Principal principal) {
        try {
            transferService.updateTransferStatus(principal, id, statusId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating transfer status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // Endpoint to get account balance by user ID
    @GetMapping("/account-balance/{userId}")
    public ResponseEntity<BigDecimal> getAccountBalanceByUserId(@PathVariable int userId) {
        try {
            BigDecimal balance = accountDao.getBalance(userId);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            logger.error("Error retrieving balance for user ID: " + userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> getAccountId(@PathVariable int accountId) {
        try {
            Account balance = accountDao.getAccountById(accountId);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            logger.error("Error retrieving balance for account ID: " + accountId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to get user by user ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserByUserId(@PathVariable int id) {
        try {
            User user = userDao.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error retrieving user with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}