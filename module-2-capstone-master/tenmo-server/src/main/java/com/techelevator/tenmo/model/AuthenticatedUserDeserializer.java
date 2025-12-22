package com.techelevator.tenmo.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Custom deserializer for AuthenticatedUser class
public class AuthenticatedUserDeserializer extends JsonDeserializer<AuthenticatedUser> {

    @Override
    public AuthenticatedUser deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        // Get the ObjectMapper from the JsonParser
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        // Read the JSON content into a JsonNode
        JsonNode node = mapper.readTree(jp);

        // Create a new AuthenticatedUser object
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        // Set the token from the JSON
        authenticatedUser.setToken(node.get("token").asText());

        // Get the "user" node from the JSON
        JsonNode userNode = node.get("user");
        // Extract user ID and username
        int userId = userNode.get("id").asInt();
        String username = userNode.get("username").asText();
        // Create a new User object with extracted data
        AuthenticatedUser.User user = new AuthenticatedUser.User(userId, username);

        // Initialize a list to hold authorities
        List<String> authorities = new ArrayList<>();
        // Get the "authorities" node from the JSON
        JsonNode authoritiesNode = userNode.get("authorities");
        // Check if authorities is an array
        if (authoritiesNode.isArray()) {
            // If it's an array, iterate through it and add each authority
            for (JsonNode authorityNode : authoritiesNode) {
                authorities.add(authorityNode.asText());
            }
        } else if (authoritiesNode.isTextual()) {
            // If it's a single text value, add it as the only authority
            authorities.add(authoritiesNode.asText());
        }
        // Set the authorities for the user
        user.setAuthorities(authorities);

        // Set the user object in the AuthenticatedUser
        authenticatedUser.setUser(user);

        // Return the fully constructed AuthenticatedUser object
        return authenticatedUser;
    }
}