package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

// Implementation of UserSvcs interface for handling user-related operations
public class UserSvcsImpl implements UserSvcs {

    // Base URL for API endpoints
    private final String baseUrl;
    // RestTemplate for making HTTP requests
    private final RestTemplate restTemplate = new RestTemplate();
    // Authentication token for API requests
    private String authToken;

    // Constructor that sets the base URL for API requests
    public UserSvcsImpl(String url) {
        this.baseUrl = url;
    }

    // Retrieves all users from the API
    @Override
    public List<User> getAllUsers() {
        try {
            // Make a GET request to the users endpoint
            User[] userArray = restTemplate.exchange(baseUrl + "users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
            // Convert the array to a List, or return an empty list if null
            return userArray != null ? List.of(userArray) : new ArrayList<>();
        } catch (RestClientResponseException e) {
            // Handle any errors that occur during the API request
            System.out.println("Error getting all users: " + e.getRawStatusCode() + " : " + e.getStatusText());
            return new ArrayList<>();
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
}