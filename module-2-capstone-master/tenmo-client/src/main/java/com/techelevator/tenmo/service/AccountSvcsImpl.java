package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

// Implementation of AccountSvcs interface for handling account-related operations
public class AccountSvcsImpl implements AccountSvcs {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    // Constructor: Initializes the base URL for API calls
    public AccountSvcsImpl(String url) {
        this.baseUrl = url;
    }

    // Retrieves account information for a given account ID
    @Override
    public Account getAccount(int accountId) {
        try {
            ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "account/" + accountId, HttpMethod.GET, makeAuthEntity(), Account.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Error getting account: " + e.getRawStatusCode() + " : " + e.getStatusText());
            return null;
        }
    }

    // Retrieves the balance for the authenticated user's account
    @Override
    public BigDecimal getBalance(AuthenticatedUser user) {
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "account/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Error getting balance: " + e.getRawStatusCode() + " : " + e.getStatusText());
            return null;
        }
    }

    // Updates an existing account with new information
    @Override
    public boolean updateAccount(Account newAccount) {
        try {
            restTemplate.exchange(baseUrl + "account", HttpMethod.PUT, makeAuthEntity(newAccount), Void.class);
            return true;
        } catch (RestClientResponseException e) {
            System.out.println("Error updating account: " + e.getRawStatusCode() + " : " + e.getStatusText());
            return false;
        }
    }

    // Creates a new account
    @Override
    public Account createAccount(Account newAccount) {
        try {
            ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "account", HttpMethod.POST, makeAuthEntity(newAccount), Account.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Error creating account: " + e.getRawStatusCode() + " : " + e.getStatusText());
            return null;
        }
    }

    // Sets the authentication token for API requests
    @Override
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    // Creates an HttpEntity with authentication headers for API requests
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    // Creates an HttpEntity with authentication headers and a body for API requests
    private <T> HttpEntity<T> makeAuthEntity(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(body, headers);
    }
}