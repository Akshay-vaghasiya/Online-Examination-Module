package com.example.backend.Service;

import com.example.backend.Dto.JwtRequest;
import com.example.backend.Dto.JwtResponse;
import com.example.backend.Dto.RegisterRequest;
import com.example.backend.Dto.ResetPasswordRequest;
import com.example.backend.Entity.PasswordResetToken;
import com.example.backend.Entity.University;
import com.example.backend.Entity.User;
import com.example.backend.Repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/* This service coordinates authentication-related operations, such as registering users, generating and
   validating JWT tokens, and managing password reset requests. It interacts with several other services and
   repositories, including UserService, JWTService, EmailService, and PasswordResetTokenRepository. */
@Service
public class AuthService {

    // Injects the AuthenticationManager for handling authentication
    @Autowired
    private AuthenticationManager authenticationManager;

    // Injects JWTService to generate JWT tokens
    @Autowired
    private JwtService jwtService;

    // Injects EmailService to send emails, like password reset requests
    @Autowired
    private EmailService emailService;

    // Injects UserService to manage User data
    @Autowired
    private UserService userService;

    // Injects PasswordResetTokenRepository for token management
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    // Injects UniversityService to manage University data
    @Autowired
    private UniversityService universityService;

    // Encrypts passwords using BCrypt with a strength of 12
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /* Registers a new user with role parameter. Encodes the password before saving and delegates user saving to UserService.

     param registerRequest - Contains user details, such as email, username, password and university.
     param role - Contains role of user which will register.
     return - ResponseEntity indicating the registration success or error status. */
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest, User.Role role) {
        User user = new User();
        user.setPassword(encoder.encode(registerRequest.getPassword())); // Encodes password
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setRole(role);

        University university = universityService.getUniversityByName(registerRequest.getUniversity());
        user.setUniversity(university);

        try {
            userService.saveUser(user); // Saves user data
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong, please try again.");
        }

        return ResponseEntity.ok("User registered successfully");
    }

    /* Authenticates a user with the provided email and password. If successful, generates a JWT token and
       checks user role for role-based access control.

     param jwtRequest - Contains user credentials (email and password).
     param role - The expected user role (e.g., STUDENT or ADMIN) for authorization.
     return - ResponseEntity containing the JwtResponse with token if authentication is successful. */
    public ResponseEntity<?> loginUser(JwtRequest jwtRequest, String role) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword())
        );

        User user = userService.findUserByEmail(jwtRequest.getEmail());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.getRole().toString().equalsIgnoreCase(role)) {
            throw new RuntimeException("Unauthorized access");
        }

        if (authentication.isAuthenticated()) {
            String userName = user.getUsername();
            String token = jwtService.generateToken(jwtRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(userName, token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    /* Initiates the password reset process by generating a reset token and sending it to the user's email.

     param email - The email address of the user requesting password reset.
     return - String message indicating whether the password reset email was sent. */
    public ResponseEntity<?> forgotPassword(String email)
    {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user found with this email");
        }

        String token = createPasswordResetToken(user);
        emailService.sendPasswordResetEmail(user.getEmail(), token);

        return ResponseEntity.ok("Password reset email sent");

    }

    /* Generates a new password reset token for the specified user and saves it in the database with an expiry date.

     param user - The user requesting password reset.
     return - The generated password reset token. */
    public String createPasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        return tokenRepository.save(passwordResetToken).getToken();
    }

    /* Resets the user's password if the provided token is valid and not expired.

     param request - Contains reset password credentials such as token and newPassword
     return - String message indicating the success or failure of the reset process. */
    public ResponseEntity<?> resetPassword(ResetPasswordRequest request)
    {
        PasswordResetToken resetToken = tokenRepository.findByToken(request.getToken());

        if (resetToken == null || isTokenExpired(resetToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token or password reset failed");
        }

        User user = resetToken.getUser();
        user.setPassword(encoder.encode(request.getNewPassword()));
        userService.saveUser(user);
        tokenRepository.delete(resetToken);

        return ResponseEntity.ok("Password reset successful");
    }

    /* Checks if the provided PasswordResetToken is expired.

     param token - The PasswordResetToken to be checked.
     return true if the token has expired; otherwise, false. */
    public boolean isTokenExpired(PasswordResetToken token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }
}

