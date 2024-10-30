package com.example.backend.Service;

import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Automatically injects an instance of UserRepository
    @Autowired
    private UserRepository userRepository;

    /* Saves a new user or updates an existing user in the database.

     param user - The User object to save. */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /* Retrieves a list of all users from the database.

     return - A list of all users. */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /* Finds a user by their unique ID.

     param id - The ID of the user to find.
     return - An Optional containing the user if found, otherwise empty. */
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    /* Deletes a user by their unique ID.

     param id - The ID of the user to delete.
     */
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    /* Finds a user by their email address.

     param email - The email of the user to find.
     return - The User object if found, otherwise null. */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}

