package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;

// JDBC implementation of the AccountDao interface
@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    // Constructor injection of JdbcTemplate
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Retrieves an Account object associated with a given user ID
    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return mapRowToAccount(results);
        }
        return null;
    }

    // Gets the balance of an account associated with a given user ID
    @Override
    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return results.getBigDecimal("balance");
        }
        return BigDecimal.ZERO;
    }

    // Updates the balance of an account associated with a given user ID
    @Override
    public void updateBalance(int userId, BigDecimal newBalance) {
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, newBalance, userId);
            if (rowsAffected == 0) {
                throw new DataAccessException("Failed to update balance for user ID: " + userId) {};
            }
        } catch (DataAccessException e) {
            throw new DataAccessException("Error updating balance for user ID: " + userId, e) {};
        }
    }

    // Retrieves an Account object by its account ID
    @Override
    public Account getAccountById(int accountId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (results.next()) {
            return mapRowToAccount(results);
        }
        return null;
    }

    // Helper method to map a database row to an Account object
    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}