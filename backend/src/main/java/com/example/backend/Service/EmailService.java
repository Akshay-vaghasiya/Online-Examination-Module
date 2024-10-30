package com.example.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // Injects an instance of JavaMailSender to send emails
    @Autowired
    private JavaMailSender mailSender;

    // Reads the base URL for password reset from environment variables
    @Value("${PASSWORD_RESET_URL}")
    private String url;

/* Sends a password reset email to the specified recipient. This method generates a complete password
   reset URL by appending the provided token to the base URL. It then composes and sends an email
   with a reset link, allowing the user to reset their password securely.

   param to - The email address of the recipient.
   param token - The unique token generated for the password reset request. */
public void sendPasswordResetEmail(String to, String token) {

        url = url + token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setText("Click the following link to reset your password: " + url);
        mailSender.send(mailMessage);
    }
}
