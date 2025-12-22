// RegisterUserDto.java
package com.techelevator.tenmo.dto;

import javax.validation.constraints.NotEmpty;

// Data Transfer Object for user registration
public class RegisterUserDto {
    // Username field, must not be empty
    @NotEmpty
    private String username;

    // Password field, must not be empty
    @NotEmpty
    private String password;

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}