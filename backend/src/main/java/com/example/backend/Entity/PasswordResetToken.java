package com.example.backend.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/* The PasswordResetToken class is an entity representing a token used for password reset functionality. This token
   links to a specific user and has an expiration date to ensure secure, time-limited access for resetting passwords.

 * Purpose:
  * This class is used to securely manage password reset tokens for users. When a password reset request is initiated,
    a token is generated, saved in this entity, and associated with the requesting user. The token and expiry date are
    then used to validate the reset request.

 * Fields:
  - id: A unique identifier for each token entry, generated automatically.It is a primary key for the entity
  - token: A unique string token sent to the user for verifying their password reset request.
  - user: A reference to the User entity associated with the reset token, ensuring each token is linked to a specific user.
  - expiryDate: Stores the date and time when the token expires, providing a time limit for the password reset to be completed.

 * Relationships:
  - @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER): Establishes a one-to-one relationship with the
    User entity, indicating each user has one reset token.
  - @JoinColumn(nullable = false, name = "user_id"): Specifies that this token entry must have a non-null user_id
    column, linking each token to a user. */
@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime expiryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
