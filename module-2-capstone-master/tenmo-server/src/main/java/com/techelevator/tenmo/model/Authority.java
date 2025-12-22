package com.techelevator.tenmo.model;

import java.util.Objects;

// This class represents an Authority in the tenmo application
public class Authority {

   // The name of the authority
   private String name;

   // Getter method for the authority name
   public String getName() {
      return name;
   }

   // Setter method for the authority name
   public void setName(String name) {
      this.name = name;
   }

   // Constructor that initializes the authority with a name
   public Authority(String name) {
      this.name = name;
   }

   // Overridden equals method for comparing Authority objects
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Authority authority = (Authority) o;
      return name.equals(authority.name);
   }

   // Overridden hashCode method for generating a hash code for Authority objects
   @Override
   public int hashCode() {
      return Objects.hash(name);
   }

   // Overridden toString method for string representation of Authority objects
   @Override
   public String toString() {
      return "Authority{" +
              "name=" + name +
              '}';
   }
}