package com.example.backend.Config;

import com.example.backend.Service.CustomUserDetailService;
import com.example.backend.Service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    // Injects JWTService for handling token operations
    @Autowired
    private JwtService jwtService;

    // Injects CustomUserDetailService for loading user details by email
    @Autowired
    private CustomUserDetailService userDetailsService;

    /* Filters each incoming request to validate the JWT token (if present) in the Authorization header.
        - Extracts the JWT token and verifies its validity.
        - If valid, sets the user authentication in the SecurityContext.
        - If token is invalid, malformed, or expired, writes an appropriate error response.

     param request - the HTTP request to filter
     param response - the HTTP response to write to
     param filterChain - the filter chain to pass the request and response to the next filter */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {

                email = jwtService.extractEmail(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                writeErrorResponse(response, HttpStatus.BAD_REQUEST, "Invalid token format.");
                return;
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token has expired. Please log in again.");
                return;
            } catch (MalformedJwtException e) {
                logger.info("Some changes have been made in token !! Invalid Token");
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid token. Please provide a valid token.");
                return;
            } catch (Exception e) {
                logger.error("An unexpected error occurred: " + e.getMessage());
                writeErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again later.");
                return;
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                logger.info("Validation fails !!");
            }
        }

        filterChain.doFilter(request, response);
    }

    /* Writes a JSON error response with specified status and message.
        - Sets the HTTP status code and content type.
        - Constructs a JSON object with error details and writes it to the response.

     param response - the HTTP response to write the error message to
     param status - the HTTP status code to set for the response
     param message - the error message to include in the response
     throws IOException - if an input or output error occurs while writing to the response */
    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now().toString());
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorDetails));
    }
}

