package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.service.TransferService;
import com.techelevator.tenmo.exception.TransferCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

// REST controller for account-related operations
@RestController
@RequestMapping("account")
@PreAuthorize("isAuthenticated()") // Ensures all endpoints require authentication
public class AccountController {
    // Logger for this class
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountDao accountDao;
    private final TransferService transferService;

    // Constructor injection of dependencies
    @Autowired
    public AccountController(AccountDao accountDao, TransferService transferService) {
        this.accountDao = accountDao;
        this.transferService = transferService;
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
    @PostMapping("/transfer")
    public ResponseEntity<?> createTransfer(@Valid @RequestBody TransferDto transferDto, Principal principal) {
        try {
            Transfer createdTransfer = transferService.createTransfer(principal, transferDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfer);
        } catch (TransferCreationException e) {
            logger.error("Error creating transfer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating transfer: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to request a new transfer
    @PostMapping("/request")
    public ResponseEntity<?> requestTransfer(@Valid @RequestBody TransferDto transferDto, Principal principal) {
        try {
            Transfer createdTransfer = transferService.requestTransfer(principal, transferDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfer);
        } catch (TransferCreationException e) {
            logger.error("Error requesting transfer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error requesting transfer: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to get all transfers for the authenticated user
    @GetMapping("/transfers")
    public ResponseEntity<List<Transfer>> getTransfers(Principal principal) {
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to get an account by user ID
    @GetMapping("/{userId}")
    public ResponseEntity<Account> getAccountByUserId(@PathVariable int userId) {
        try {
            Account account = accountDao.getAccountByUserId(userId);
            if (account == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            logger.error("Error retrieving account for user ID: " + userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}