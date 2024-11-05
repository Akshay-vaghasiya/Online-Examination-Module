package com.example.backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/* Entity class representing a User in the system.

 * This class maps to the "users" table in the database and contains fields for storing
   essential user details like username, email, password, role, university affiliation, and timestamps.

 * Each user is uniquely identified by the userId (primary key), and email addresses are unique across users.
 * The university field links each user to a University entity, allowing for optional affiliation with a university.

 * Timestamps:
  - createdAt: Records the account creation time, useful for tracking the account's lifecycle.
  - updatedAt: Records the last modification time for the account, useful for audit purposes.

 * The role field uses an enumeration (ADMIN, STUDENT) for type-safe role management.
 * The Role enum defines two possible roles that control access permissions within the application.

 * Relationships:
  - @ManyToOne: Links each user to a university, if applicable, establishing an optional many-to-one relationship.
  - @JoinColumn(name = "university_id", nullable = true): Specifies the foreign key column in the "users" table to reference the "university" table. */
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

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = true)
    private University university;

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

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}

