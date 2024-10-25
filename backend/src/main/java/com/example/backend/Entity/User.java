package com.example.backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/* Entity class representing a User in the system.

 * This class maps to the "users" table in the database and contains fields for storing
   essential user details like username, email, password, role, and timestamps. It supports
   two roles: ADMIN and STUDENT, which are used to control access to different parts of the application.

 * Each user is uniquely identified by the userId (primary key), and email addresses are unique across users.

 * Timestamps include:
  - createdAt: The account creation time.
  - updatedAt: The last update time for the account.

 * The role field uses an enumeration (ADMIN, STUDENT) for type-safe role management.*/
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Role {
        ADMIN,
        STUDENT
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

