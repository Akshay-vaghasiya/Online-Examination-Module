package com.example.backend.Dto;

/* The JwtRequest class is a Data Transfer Object (DTO) used to encapsulate the data required for
   authenticating a user and generating a JWT (JSON Web Token).
    * This class includes two fields: email and password, which are needed for user authentication.

 * Purpose:
  * This class serves as a simple data carrier between the client and server for authentication requests.
    The email and password provided are used to validate the user's credentials and, if valid, generate
    an access token for session management.

 * Fields:
  - email: Stores the user's unique email address, which acts as the primary identifier for the user account.
  - password: Stores the user's password, which is used to verify the user's identity. The password should be
    securely handled and transmitted.

 * Methods:
  - Getter and Setter methods for each field are provided to facilitate controlled access to the class's fields.
    This allows the authentication service to retrieve the user's credentials and validate them accordingly. */
public class JwtRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
