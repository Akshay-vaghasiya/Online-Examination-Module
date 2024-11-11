package com.example.backend.Dto;

import com.example.backend.Entity.User;

/* The UpdateUserRequest class is a Data Transfer Object (DTO) used to encapsulate data required for updating a user's profile.
   This class includes four main fields:
    * username, email, role, and university, which represent the editable attributes of a user profile.

 * Purpose:
   * This class is designed to collect and transfer data for updating user information. It serves as a data carrier
     between the client-side update form and the server-side update logic.

 * Fields:
   - username: Represents the user's display name, which may not be unique across the application.
   - email: Stores the user's email address, generally unique and used for contact and identification purposes.
   - role: Indicates the user's role within the application (e.g., ADMIN, STUDENT), defined as an enum in the User class.
   - university: Represents the name of the university associated with the user, allowing for university-specific management.

 * Methods:
   - Getter and Setter methods are provided for each field, enabling controlled access and modification of the user data fields. */
public class UpdateUserRequest {
    private String username;
    private String email;
    private User.Role role;
    private String university;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", university='" + university + '\'' +
                '}';
    }
}
