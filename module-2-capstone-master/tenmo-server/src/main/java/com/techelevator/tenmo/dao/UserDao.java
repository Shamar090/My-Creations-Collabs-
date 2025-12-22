package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.dto.RegisterUserDto;
import com.techelevator.tenmo.model.User;

import java.util.List;

// Interface defining operations for User data access
public interface UserDao {
    // Retrieves all users
    List<User> getUsers();

    // Retrieves a user by their ID
    User getUserById(int id);

    // Retrieves a user by their username
    User getUserByUsername(String username);

    // Creates a new user
    User createUser(RegisterUserDto user);

    // Retrieves a user by their username (for testing purposes)
    User getUserByUsernameForTesting(String username);
}