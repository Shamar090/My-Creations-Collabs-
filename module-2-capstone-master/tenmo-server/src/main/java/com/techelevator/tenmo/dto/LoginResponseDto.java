package com.techelevator.tenmo.dto;

import com.techelevator.tenmo.model.User;

/*
    The acronym DTO is being used for "data transfer object". It means that this type of class is specifically
    created to transfer data between the client and the server. For example, CredentialsDto represents the data a client must
    pass to the server for a login endpoint, and TokenDto represents the object that's returned from the server
    to the client from a login endpoint.
 */
public class LoginResponseDto {

    // JWT token for authenticated user
    private String token;
    // User object containing user details
    private User user;

    // Constructor to initialize token and user
    public LoginResponseDto(String token, User user) {
        this.token = token;
        this.user = user;
    }

    // Getter for token
    public String getToken() {
        return token;
    }

    // Setter for token (package-private visibility)
    void setToken(String token) {
        this.token = token;
    }

    // Getter for user
    public User getUser() {
        return user;
    }

    // Setter for user
    public void setUser(User user) {
        this.user = user;
    }
}