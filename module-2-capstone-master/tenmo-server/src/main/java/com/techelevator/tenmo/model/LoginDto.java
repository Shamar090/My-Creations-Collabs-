package com.techelevator.tenmo.model;


/*
    The acronym DTO is being used for "data transfer object". It means that this type of class is specifically
    created to transfer data between the client and the server. For example, LoginDto represents the data a client
    must pass to the server for a login endpoint, and LoginResponseDto represents the object that's returned from the server
    to the client from a login endpoint.
 */
public class LoginDto {

    // Username for login
    private String username;
    // Password for login
    private String password;

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Setter method for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Overridden toString method for string representation of LoginDto objects
    @Override
    public String toString() {
        return "LoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}