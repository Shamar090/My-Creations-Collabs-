package com.techelevator.tenmo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

// This class represents an authenticated user in the tenmo application
// It uses a custom deserializer for JSON parsing
@JsonDeserialize(using = AuthenticatedUserDeserializer.class)
public class AuthenticatedUser {

    // Authentication token for the user
    private String token;
    // User object containing user details
    private User user;

    // Getter for the authentication token
    public String getToken() {
        return token;
    }

    // Setter for the authentication token
    public void setToken(String token) {
        this.token = token;
    }

    // Getter for the user object
    public User getUser() {
        return user;
    }

    // Setter for the user object
    public void setUser(User user) {
        this.user = user;
    }

    // Inner class representing a User
    public static class User {
        // Unique identifier for the user
        private int id;
        // Username of the user
        private String username;
        // List of authorities (roles/permissions) assigned to the user
        private List<String> authorities;

        // Constructor with id and username parameters
        public User(int id, String username) {
        }

        // Default constructor
        public User() {

        }

        // Getter for user id
        public int getId() {
            return id;
        }

        // Setter for user id
        public void setId(int id) {
            this.id = id;
        }

        // Getter for username
        public String getUsername() {
            return username;
        }

        // Setter for username
        public void setUsername(String username) {
            this.username = username;
        }

        // Getter for authorities
        public List<String> getAuthorities() {
            return authorities;
        }

        // Setter for authorities
        public void setAuthorities(List<String> authorities) {
            this.authorities = authorities;
        }
    }
}
