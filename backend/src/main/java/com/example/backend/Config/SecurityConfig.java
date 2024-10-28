package com.example.backend.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Injects a custom UserDetailsService implementation for loading user data during authentication
    private UserDetailsService userDetailsService;

    // Injects the JWT filter to intercept requests and validate JWT tokens before authentication
    @Autowired
    private JwtFilter jwtFilter;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /* Configures the security filter chain to define application security settings.
        - Disables CSRF protection for a stateless RESTful API.
        - Requires authentication for all requests by default.
        - Enables HTTP Basic Authentication for simplicity.
        - Sets session management policy to stateless, as JWT tokens are used for stateful user sessions.
        - Adds JwtFilter before the UsernamePasswordAuthenticationFilter to validate JWT tokens on each request.

     param http - the HttpSecurity configuration for managing security
     return - the configured SecurityFilterChain instance
     throws Exception - if an error occurs during filter chain setup */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /* Configures the authentication provider with password encoding and user details service.
        - Uses BCryptPasswordEncoder for secure password hashing with a strength of 12.
        - Leverages custom UserDetailsService to retrieve user-specific data during authentication. */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /* Configures a custom AuthenticationManager bean for handling authentication requests.
        - Retrieves the default AuthenticationManager from the application’s configured AuthenticationProvider.
        - Allows injection of the AuthenticationManager into other components for custom authentication handling.

     param config - the AuthenticationConfiguration instance used to build the AuthenticationManager
     return - the configured AuthenticationManager instance
     throws Exception - if an error occurs while retrieving the AuthenticationManager */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

