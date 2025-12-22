package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

// Abstract base class for service classes, providing common functionality for API interactions
public abstract class BaseService {
    // Base URL for API endpoints
    protected final String API_BASE_URL;
    // RestTemplate for making HTTP requests
    protected final RestTemplate restTemplate = new RestTemplate();

    // Default constructor that sets the API base URL to localhost
    public BaseService() {
        this.API_BASE_URL = "http://localhost:8080/";
    }

    // Constructor that allows specifying a custom API base URL
    public BaseService(String url) {
        this.API_BASE_URL = url;
    }

    // Creates an HttpEntity with authentication headers for API requests
    protected HttpEntity<?> createHttpEntity(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        return new HttpEntity<>(headers);
    }

    // Creates an HttpEntity with authentication headers and a body for API requests
    protected <T> HttpEntity<?> createHttpEntity(AuthenticatedUser user, T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        return new HttpEntity<>(body, headers);
    }
}
