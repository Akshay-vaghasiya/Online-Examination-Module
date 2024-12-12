package com.example.backend.Dto;

import com.example.backend.Entity.User;

/* The UserResponse class is a Data Transfer Object (DTO) used to encapsulate the data returned to clients
   when fetching user information. This class contains five main fields:
    * userid, username, email, role, branch, semester and university, which provide key details about a user.

 * Purpose:
   * This class is designed to transfer user information between the server and client in a simplified, readable format.
     It helps separate internal user data structures from the data exposed through APIs, ensuring secure and clean data transfer.

 * Fields:
   - userid: Represents the unique identifier for the user, typically used to track users across the application.
   - username: Contains the user's display name, which may not be unique across the application.
   - email: Holds the user's email address, used for contact and unique identification purposes.
   - role: Specifies the role of the user (e.g., ADMIN, STUDENT), which is an enumerated type within the User class.
   - university: Stores the name of the university associated with the user. This field defaults to an empty string if no university is specified.
   - branch: Stores the branch of user, which used in show exam to particular user.
   - semester: Stores the semester of user, which is also helpful to show exam to user.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of user data. */
public class UserResponse {
    private Long userid;
    private String username;
    private String email;
    private User.Role role;
    private String branch;
    private int semester=0;
    private String university = "";

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
