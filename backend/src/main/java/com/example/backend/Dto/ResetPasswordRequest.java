package com.example.backend.Dto;

/* The ResetPasswordRequest class is a Data Transfer Object (DTO) used to encapsulate data needed for a password reset request.
   This class contains two main fields:
    * token and newPassword, which are required to verify the request and update the user's password.

 * Purpose:
   * This class is designed to gather and transfer the data necessary for resetting a user's password. It acts as a
     simple data carrier between the client and the server-side password reset logic.

 * Fields:
   - token: A unique identifier (typically a JWT or similar) sent to the user's email to authorize the password reset request.
   - newPassword: The new password that the user wants to set, which will replace the previous password upon successful verification.

 * Methods:
   - Getter and Setter methods for each field are provided to enable controlled access and modification of the token and newPassword fields. */
public class ResetPasswordRequest {

    private String token;
    private String newPassword;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}