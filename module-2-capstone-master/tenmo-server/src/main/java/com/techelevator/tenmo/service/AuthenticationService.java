package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dto.LoginDto;
import com.techelevator.tenmo.dto.RegisterUserDto;
import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

// Service class for handling authentication-related operations
public class AuthenticationService {

    // Base URL for the authentication API
    private final String baseUrl;
    // RestTemplate for making HTTP requests
    private final RestTemplate restTemplate;

    // Constructor to initialize the service with a base URL
    public AuthenticationService(String url) {
        this.baseUrl = url.endsWith("/") ? url : url + "/";
        this.restTemplate = new RestTemplate();
    }

    // Method to authenticate a user and return an AuthenticatedUser object
    public AuthenticatedUser login(LoginDto credentials) {
        // Set up HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDto> entity = new HttpEntity<>(credentials, headers);

        try {
            // Make a POST request to the login endpoint
            ResponseEntity<AuthenticatedUser> response =
                    restTemplate.exchange(baseUrl + "api/auth/login", HttpMethod.POST, entity, AuthenticatedUser.class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            // Log error and return null if login fails
            System.out.println("Error during login: " + e.getMessage());
            return null;
        }
    }

    // Method to register a new user
    public boolean register(RegisterUserDto credentials) {
        // Set up HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterUserDto> entity = new HttpEntity<>(credentials, headers);

        try {
            // Make a POST request to the register endpoint
            ResponseEntity<Void> response =
                    restTemplate.exchange(baseUrl + "api/auth/register", HttpMethod.POST, entity, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientResponseException e) {
            // Log error details if registration fails due to client/server error
            System.out.println("Error during registration: " + e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
            return false;
        } catch (ResourceAccessException e) {
            // Log error if registration fails due to network issues
            System.out.println("Error during registration: " + e.getMessage());
            return false;
        }
    }
}