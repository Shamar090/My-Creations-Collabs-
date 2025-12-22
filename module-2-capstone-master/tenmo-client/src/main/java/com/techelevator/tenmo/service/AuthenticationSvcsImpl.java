package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

// Implementation of AuthenticationSvcs interface for handling user authentication
public class AuthenticationSvcsImpl implements AuthenticationSvcs {

    // Base URL for API endpoints
    private final String baseUrl;
    // RestTemplate for making HTTP requests
    private final RestTemplate restTemplate = new RestTemplate();

    // Constructor: Ensures the base URL ends with a slash
    public AuthenticationSvcsImpl(String url) {
        this.baseUrl = url.endsWith("/") ? url : url + "/";
    }

    // Attempts to log in a user with the provided credentials
    @Override
    public AuthenticatedUser login(UserCredentials credentials) {
        HttpEntity<UserCredentials> entity = createCredentialsEntity(credentials);
        AuthenticatedUser user = null;
        try {
            // Send POST request to login endpoint
            user = restTemplate.exchange(baseUrl + "login", HttpMethod.POST, entity, AuthenticatedUser.class).getBody();
        } catch (RestClientResponseException e) {
            // Handle HTTP error responses
            System.out.println("Error logging in: " + e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            // Handle network or other I/O errors
            System.out.println("Error logging in: " + e.getMessage());
        }
        return user;
    }

    // Attempts to register a new user with the provided credentials
    @Override
    public boolean register(UserCredentials credentials) {
        HttpEntity<UserCredentials> entity = createCredentialsEntity(credentials);
        boolean success = false;
        try {
            // Send POST request to register endpoint
            restTemplate.exchange(baseUrl + "register", HttpMethod.POST, entity, Void.class);
            success = true;
        } catch (RestClientResponseException e) {
            // Handle HTTP error responses
            System.out.println("Error registering user: " + e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            // Handle network or other I/O errors
            System.out.println("Error registering user: " + e.getMessage());
        }
        return success;
    }

    // Creates an HttpEntity with the user credentials and appropriate headers
    private HttpEntity<UserCredentials> createCredentialsEntity(UserCredentials credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(credentials, headers);
    }
}