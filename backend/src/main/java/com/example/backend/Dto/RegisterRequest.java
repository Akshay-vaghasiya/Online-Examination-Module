package com.example.backend.Dto;

/* The RegisterRequest class is a Data Transfer Object (DTO) used to encapsulate the data needed to
   register a new user. This class contains three main fields:
    * email, username, and password, which are required for user registration.

 * Purpose:
  * This class is designed to collect and validate user registration information before it is processed
    by the application. It acts as a simple data carrier between the client-side registration form and
    the server-side registration logic.

 * Fields:
  - email: Stores the user's unique email address, typically used for contact and user identification purposes.
  - username: Stores the user's chosen display name, which does not have to be unique across the application.
  - password: Stores the user's chosen password, which should be securely processed and stored following best practices.

 * Methods:
  - Getter and Setter methods for each field are provided to allow controlled access and modification of the data fields.*/
public class RegisterRequest {
    private String email;
    private String username;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
