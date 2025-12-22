package com.techelevator.tenmo.dto;

// Data Transfer Object for User information
public class UserDto {

    // User ID
    private int id;
    // Username
    private String username;

    // Constructor to initialize id and username
    public UserDto(int id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
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

    // Override toString method for object representation
    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}