package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataAccessException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// JDBC implementation of the TransferDao interface
@Component
public class JdbcTransferDao implements TransferDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcTransferDao.class);
    private final JdbcTemplate jdbcTemplate;

    // Constants for transfer status
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_APPROVED = 2;
    public static final int STATUS_REJECTED = 3;

    // Constructor injection of JdbcTemplate
    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Creates a new transfer in the database
    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (account_from, account_to, amount, transfer_type_id, transfer_status_id) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";
        try {
            Integer newId = jdbcTemplate.queryForObject(sql, Integer.class,
                    transfer.getAccountFrom(),
                    transfer.getAccountTo(),
                    transfer.getAmount(),
                    transfer.getTransferTypeId(),
                    transfer.getTransferStatusId());

            if (newId == null) {
                throw new DaoException("Failed to create transfer - no ID returned.");
            }

            log.info("Transfer created successfully with ID: {}", newId);
            return getTransferById(newId);
        } catch (DataAccessException e) {
            log.error("Database error creating transfer: {}", e.getMessage(), e);
            throw new DaoException("Database error creating transfer", e);
        }
    }

    // Retrieves a transfer by its ID
    @Override
    public Transfer getTransferById(int transferId) {
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if (results.next()) {
                return mapRowToTransfer(results);
            } else {
                log.info("No transfer found with ID: {}", transferId);
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Database error getting transfer by ID: {}", transferId, e);
            throw new DaoException("Database error getting transfer", e);
        }
    }

    // Retrieves all transfers for a specific user
    @Override
    public List<Transfer> getTransfersForUser(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.* FROM transfer t " +
                "JOIN account a ON t.account_from = a.account_id OR t.account_to = a.account_id " +
                "WHERE a.user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                transfers.add(mapRowToTransfer(results));
            }
            log.info("Retrieved {} transfers for user ID: {}", transfers.size(), userId);
            return transfers;
        } catch (DataAccessException e) {
            log.error("Database error getting transfers for user ID: {}", userId, e);
            throw new DaoException("Database error getting transfers", e);
        }
    }

    // Updates the status of a transfer
    @Override
    public void updateTransferStatus(int transferId, int statusId) {
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, statusId, transferId);
            if (rowsAffected == 0) {
                throw new DaoException("Failed to update transfer status for transfer ID: " + transferId);
            }
            log.info("Transfer status updated for transfer ID: {} to status: {}", transferId, statusId);
        } catch (DataAccessException e) {
            log.error("Database error updating transfer status for transfer ID: {}", transferId, e);
            throw new DaoException("Database error updating transfer status", e);
        }
    }

    // Retrieves all pending transfers for a specific user
    @Override
    public List<Transfer> getPendingTransfers(int userId) {
        List<Transfer> pendingTransfers = new ArrayList<>();
        String sql = "SELECT t.* FROM transfer t " +
                "JOIN account a ON t.account_from = a.account_id OR t.account_to = a.account_id " +
                "WHERE a.user_id = ? AND t.transfer_status_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, STATUS_PENDING);
            while (results.next()) {
                pendingTransfers.add(mapRowToTransfer(results));
            }
            log.info("Retrieved {} pending transfers for user ID: {}", pendingTransfers.size(), userId);
            return pendingTransfers;
        } catch (DataAccessException e) {
            log.error("Database error getting pending transfers for user ID: {}", userId, e);
            throw new DaoException("Database error getting pending transfers", e);
        }
    }

    // Helper method to map a database row to a Transfer object
    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        return transfer;
    }
}