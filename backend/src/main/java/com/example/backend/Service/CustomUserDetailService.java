package com.example.backend.Service;

import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {

    // Injects the UserRepository to interact with user data in the database
    @Autowired
    UserRepository userRepository;

    /* Loads the user details by email for authentication purposes.
        - Retrieves user by email from the repository.
        - Throws UsernameNotFoundException if no user is found with the specified email.
        - Converts the User object to a Spring Security UserDetails object.

     param - email the email of the user to load
     return - UserDetails containing user's email, password, and roles
     throws - UsernameNotFoundException if no user is found with the specified email */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    /* Retrieves the user's authorities (roles) and maps them to GrantedAuthority format.
        - Prefixes each role with 'ROLE_' to align with Spring Security's role naming convention.

     param - user the user whose authorities are to be retrieved
     return - a collection of GrantedAuthority for the specified user
     */
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())
        );
    }
}

