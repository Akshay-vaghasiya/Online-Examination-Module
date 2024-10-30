package com.example.backend.Controller;

import com.example.backend.Dto.JwtRequest;
import com.example.backend.Dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* The AuthController class is a REST controller responsible for handling authentication and authorization
   requests. It provides endpoints for user registration, login (for both students and admins), and password
   reset operations.

 * Purpose:
  - This controller manages HTTP requests related to user authentication  and authorization. It delegates
    the processing of each request to the AuthService, which handles the core business logic.

 * Endpoints:
  - /register: Handles new user registration.
  - /login-student: Authenticates a student user.
  - /login-admin: Authenticates an admin user.
  - /forgot-password: Initiates the password reset process by generating a password reset token and sending it to the user's email.
  - /reset-password: Completes the password reset by verifying the token and updating the user’s password. */
@RestController
@RequestMapping("/api/auth") // Base URL for all authentication-related endpoints
public class AuthController {

    // Injects AuthService to handle authentication logic
    @Autowired
    private AuthService authService;

    /* Registers a new user in the system.

     param registerRequest - The request body containing user details.
     return - ResponseEntity with the registration status or user details. */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    /* Authenticates a student user and generates a JWT token upon successful login.

     param jwtRequest - The request body containing login credentials.
     return - ResponseEntity with authentication status and token if successful. */
    @PostMapping("/login-student")
    public ResponseEntity<?> loginStudent(@RequestBody JwtRequest jwtRequest) {
        return authService.loginUser(jwtRequest, "STUDENT");
    }

    /* Authenticates an admin user and generates a JWT token upon successful login.

     param jwtRequest - The request body containing login credentials.
     return - ResponseEntity with authentication status and token if successful. */
    @PostMapping("/login-admin")
    public ResponseEntity<?> loginAdmin(@RequestBody JwtRequest jwtRequest) {
        return authService.loginUser(jwtRequest, "ADMIN");
    }

    /* Initiates the password reset process by generating a token and sending it to the user's email address.

     param email - The email address of the user requesting password reset.
     return - A string message indicating the status of the password reset request. */
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email) {
        return authService.forgotPassword(email);
    }

    /* Completes the password reset process by validating the token and updating the user’s password.

     param token - The password reset token received by the user.
     param newPassword - The new password to be set for the user.
     return - A string message indicating the result of the password reset operation. */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        return authService.resetPassword(token, newPassword);
    }
}


