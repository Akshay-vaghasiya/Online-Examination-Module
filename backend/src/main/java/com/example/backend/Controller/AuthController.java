package com.example.backend.Controller;

import com.example.backend.Dto.JwtRequest;
import com.example.backend.Dto.RegisterRequest;
import com.example.backend.Dto.ResetPasswordRequest;
import com.example.backend.Entity.User;
import com.example.backend.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/* The AuthController class is a REST controller responsible for handling authentication and authorization
   requests. It provides endpoints for user registration, login (for both students and admins), and password
   reset operations.

 * Purpose:
  - This controller manages HTTP requests related to user authentication  and authorization. It delegates
    the processing of each request to the AuthService, which handles the core business logic.

 * Endpoints:
  - /register-student: Handles new student registration.
  - /register-student-excel: Handles new student registration by excel file.
  - /register-admin: Handles new admin registration.
  - /login-student: Authenticates a student user.
  - /login-admin: Authenticates an admin user.
  - /forgot-password: Initiates the password reset process by generating a password reset token and sending it to the user's email.
  - /reset-password: Completes the password reset by verifying the token and updating the user’s password. */
@RestController
@RequestMapping("/api/auth") // Base URL for all authentication-related endpoints
/* This annotation allows cross-origin requests from any origin to access this controller's endpoints. It is
used to enable Cross-Origin Resource Sharing (CORS), which is useful for enabling requests from different domains,
especially in development environments or when the frontend and backend are hosted on different servers. */
@CrossOrigin("*")
public class AuthController {

    // Injects AuthService to handle authentication logic
    @Autowired
    private AuthService authService;

    /* Registers a new student in the system.

     param registerRequest - The request body containing student details.
     return - ResponseEntity with the registration status or student details. */
    @PostMapping("/register-student")
    public ResponseEntity<?> registerStudent(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest, User.Role.STUDENT);
    }

    /* Registers new students in the system by upload excel site.

     param file - Contains uploaded file in frontend.
     return - ResponseEntity with the registration status or student details. */
    @PostMapping("/register-student-excel")
    public ResponseEntity<?> registerStudentExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return authService.registerUserExcel(file);
    }

    /* Registers a new admin in the system.

     param registerRequest - The request body containing admin details.
     return - ResponseEntity with the registration status or admin details. */
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest, User.Role.ADMIN);
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
     return - ResponseEntity with message if successful or fail. */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        return authService.forgotPassword(email);
    }

    /* Completes the password reset process by validating the token and updating the user’s password.

     param token - The password reset token received by the user.
     param newPassword - The new password to be set for the user.
     return - ResponseEntity with message if successful or fail. */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }
}


