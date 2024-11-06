package com.example.backend.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Injects the secret key used to sign and verify JWTs, sourced from environment variables or properties
    @Value("${SECRET_KEY}")
    private String secretKey;

    /* Generates a JWT token for a specified user with their email and password.
        - Adds email as a claim.
        - Sets subject as email, issue time as current time, and expiration time as 10 hours.
        - Signs the token using the secret key.

     param email - the user's email to set as subject and claim
     param password - the user's password (not used in claims, but could be for additional payload)
     return - a signed JWT token as a String */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .and()
                .signWith(getKey())
                .compact();
    }

    /* Decodes and returns the secret key used for signing and verification of JWTs.
        - Decodes the Base64-encoded secret key string.
        - Uses the decoded key bytes to create an HMAC SHA key.

     return - SecretKey instance for JWT signing and verification */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /* Extracts the email (subject) from the given JWT token.

     param token - the JWT token from which to extract the email
     return - the extracted email as the token's subject */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /* Extracts a specific claim from the token by applying a resolver function.

     param token - the JWT token
     param claimResolver - a function that resolves and returns the desired claim
     param <T> - the type of claim to extract
     return - the resolved claim as specified by claimResolver */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /* Parses the JWT token to extract all claims.
        - Verifies the token using the secret key.

     param token - the JWT token to parse
     return - Claims object containing all claims from the token */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey()) // Verify token signature with secret key
                .build()
                .parseSignedClaims(token) // Parses signed claims from the token
                .getPayload();
    }

    /* Validates a JWT token by checking its email against the user's email and ensuring it is not expired.

     param token - the JWT token to validate
     param userDetails - the user details containing the email to compare
     return - true if the token is valid and matches the user email, false otherwise */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /* Checks if a JWT token is expired based on its expiration date.

     param token - the JWT token to check
     return - true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    /* Extracts the expiration date from the JWT token.

     param token - the JWT token from which to extract expiration
     return - Date object representing the token's expiration date */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

