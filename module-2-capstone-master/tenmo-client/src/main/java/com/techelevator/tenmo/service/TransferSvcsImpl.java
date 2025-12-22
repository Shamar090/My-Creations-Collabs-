package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import com.techelevator.tenmo.dto.TransferDto;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Implementation of TransferSvcs interface for handling transfer-related operations
public class TransferSvcsImpl implements TransferSvcs {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    // Constructor: Ensures the base URL ends with a slash
    public TransferSvcsImpl(String url) {
        this.baseUrl = url.endsWith("/") ? url : url + "/";
    }

    // Sets the authentication token for API requests
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    // Retrieves the account balance for the authenticated user
    @Override
    public BigDecimal getBalance() {
        ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "account/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
        return response.getBody();
    }

    // Retrieves a list of all users in the system
    @Override
    public List<User> getAllUsers() {
        ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "account/users", HttpMethod.GET, makeAuthEntity(), User[].class);
        return Arrays.asList(response.getBody());
    }

    // Creates a new transfer
    @Override
    public Transfer createTransfer(TransferDto transferDto) {
        ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "account/transfer", HttpMethod.POST, makeAuthEntity(transferDto), Transfer.class);
        return response.getBody();
    }

    // Requests a new transfer
    @Override
    public Transfer requestTransfer(TransferDto transferDto) {
        ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "account/request", HttpMethod.POST, makeAuthEntity(transferDto), Transfer.class);
        return response.getBody();
    }

    // Retrieves all transfers for the authenticated user
    @Override
    public List<Transfer> getTransfersForUser() {
        ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "account/transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public Map<Integer, Transfer> getTransferForUser() {
        Map<Integer, Transfer> transfers = null;
        ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "account/transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
        if(response.getBody().length == 0){
            return transfers;
        }
        transfers = arrayToLinkedHashMap(response.getBody());
        return transfers;
    }

    // Retrieves all pending transfers for the authenticated user
    @Override
    public Map<Integer, Transfer> getPendingTransfer () {
        ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "account/transfers/pending", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
        return arrayToLinkedHashMap(response.getBody());
    }


    private Map<Integer, Transfer> arrayToLinkedHashMap(Transfer[] array) {
        Map<Integer,Transfer> transfersMap = new LinkedHashMap<Integer,Transfer>();
        for (Transfer transfer : array) {
            transfersMap.put(transfer.getTransferId(), transfer);
        }
        return transfersMap;
    }

    // Retrieves all pending transfers for the authenticated user
    @Override
    public List<Transfer> getPendingTransfers() {
        ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "account/transfers/pending", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
        return Arrays.asList(response.getBody());
    }

    // Updates the status of a transfer
    @Override
    public void updateTransferStatus(int transferId, int statusId) {
        restTemplate.exchange(baseUrl + "transfers/" + transferId + "?statusId=" + statusId, HttpMethod.PUT, makeAuthEntity(), Void.class);
    }

    // Retrieves the account balance for a specific user
    @Override
    public BigDecimal getAccountByUserId(int userId) {
        ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "account/" + userId, HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
        return response.getBody();
    }

    // Retrieves the userID using accountID
    @Override
    public int getAccountByAccId(int accountId) {
        ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "accounts/" + accountId, HttpMethod.GET, makeAuthEntity(), Account.class);
        return response.getBody().getUserId();
    }


    // Retrieves user information by user ID
    @Override
    public User getUserByUserId(int id) {
        ResponseEntity<User> response = restTemplate.exchange(baseUrl + "users/" + id, HttpMethod.GET, makeAuthEntity(), User.class);
        return response.getBody();
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



    //get username of ID
    public String getUserNameByUserId(int id) {
        User user = null;
        try {
            ResponseEntity<User> response = restTemplate.exchange(baseUrl + "users/" + id, HttpMethod.GET, makeAuthEntity(), User.class);

            user = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return user.getUsername();
    }
}