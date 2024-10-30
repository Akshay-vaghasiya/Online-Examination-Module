package com.example.backend.Dto;

/* The JwtResponse class is a Data Transfer Object (DTO) that encapsulates the response data for successful
   JWT authentication. This class contains fields for the generated JWT token and the associated username,
   which are returned to the client upon successful login.

 * Purpose:
  * This class is designed to package the JWT token and the username, providing them in a structured format
    to the client following successful authentication. The JWT token can then be used for secure session
    management in subsequent requests by the client.

 * Fields:
  - jwtToken: Stores the generated JWT token string, which is used for authorizing future requests from
    the authenticated user. This token provides secure, stateless authentication for the user's session.
  - username: Stores the username of the authenticated user. This can serve as a reference for the client
    or UI to display the user's identity.

 * Constructor:
  - JwtResponse(String userName, String token): Initializes the JwtResponse with the provided username
    and JWT token. This constructor is used to create a response object upon successful login.

 * Methods:
  - Getter and Setter methods for both jwtToken and username are provided to allow controlled access
    and modification of the class's fields. */
 public class JwtResponse {

    private String jwtToken;
    private  String username;

    public JwtResponse(String userName, String token) {
        this.jwtToken = token;
        this.username = userName;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
