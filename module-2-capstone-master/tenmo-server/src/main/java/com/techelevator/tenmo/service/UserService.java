package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

// Interface defining the contract for user-related operations in the tenmo application
public interface UserService {
    // Retrieves a list of all users in the system
    List<User> findAll();

    // Retrieves a user by their unique identifier
    User getUserById(int id);

    // Finds a user by their username
    User findByUsername(String username);

    // Finds the user ID associated with a given username
    int findIdByUsername(String username);

    // Creates a new user with the given username and password
    // The password is encoded using the provided PasswordEncoder
    // Returns true if the user was successfully created, false otherwise
    // Throws an exception if there's an error during user creation
    boolean create(String username, String password, PasswordEncoder passwordEncoder) throws Exception;
}