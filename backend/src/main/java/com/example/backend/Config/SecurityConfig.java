package com.example.backend.Config;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

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
        - Requires authentication for all requests by default. Authentication related requests are public. Other requests are having role base permission.
        - Enables HTTP Basic Authentication for simplicity.
        - Sets session management policy to stateless, as JWT tokens are used for stateful user sessions.
        - Adds JwtFilter before the UsernamePasswordAuthenticationFilter to validate JWT tokens on each request.
        - Configures CORS (Cross-Origin Resource Sharing) to allow requests from specific origins, enabling interaction between the frontend and backend running on different ports.


     param http - the HttpSecurity configuration for managing security
     return - the configured SecurityFilterChain instance
     throws Exception - if an error occurs during filter chain setup */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/register-student","/api/auth/register-admin","/api/university/**"
                        , "api/user/**","api/questions/**").hasRole("ADMIN")
                        .requestMatchers( "/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /* Configures CORS settings to allow cross-origin requests from the frontend server.
        - Allowed origins: http://localhost:5173, typically the frontend development server.
        - Allows all HTTP methods and headers.
        - Enables credential sharing and sets a max age for preflight requests. */
    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setExposedHeaders(List.of("Authorization"));
                configuration.setMaxAge(3600L);

                return configuration;
            }
        };
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

