package com.techelevator.tenmo.model;

/**
 * Represents an authenticated user in the TEnmo application.
 * This class holds the authentication token and user details for a logged-in user.
 */
public class AuthenticatedUser {

	// The authentication token for the user's session
	private String token;
	// The User object containing the user's details
	private User user;

	/**
	 * Gets the authentication token for the user.
	 * @return The authentication token as a String.
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the authentication token for the user.
	 * @param token The authentication token to set.
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the User object associated with this authenticated user.
	 * @return The User object.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the User object for this authenticated user.
	 * @param user The User object to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}
}