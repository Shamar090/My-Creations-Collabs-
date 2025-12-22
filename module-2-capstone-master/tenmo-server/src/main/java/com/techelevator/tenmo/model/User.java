package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// This class represents a User in the tenmo application
public class User {

   // Unique identifier for the user
   private int id;
   // Username of the user
   private String username;
   @JsonIgnore // prevent from being sent to client
   // Password of the user
   private String password;
   @JsonIgnore
   // Indicates whether the user account is activated
   private boolean activated;
   // Set of authorities (roles) assigned to the user
   private Set<Authority> authorities = new HashSet<>();

   // Default constructor
   public User() { }

   // Constructor with all fields
   public User(int id, String username, String password, String authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      if (authorities != null) this.setAuthorities(authorities);
      this.activated = true;
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

   // Getter for password
   public String getPassword() {
      return password;
   }

   // Setter for password
   public void setPassword(String password) {
      this.password = password;
   }

   // Getter for activated status
   public boolean isActivated() {
      return activated;
   }

   // Setter for activated status
   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   // Getter for authorities
   public Set<Authority> getAuthorities() {
      return authorities;
   }

   // Setter for authorities (Set<Authority>)
   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   // Setter for authorities (String)
   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for (String role : roles) {
         this.authorities.add(new Authority("ROLE_" + role.trim()));
      }
   }

   // Overridden equals method for comparing User objects
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(authorities, user.authorities);
   }

   // Overridden hashCode method for generating a hash code for User objects
   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   // Overridden toString method for string representation of User objects
   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }
}
