package com.example.backend.Controller;

import com.example.backend.Dto.UpdateUserRequest;
import com.example.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* The UserController class is a REST controller responsible for handling requests related to
   user operations, such as retrieving, updating, and deleting user records.

 * Purpose:
  - This controller manages HTTP requests related to user data and delegates the processing
    to the UserService, which handles the core business logic for user operations.

 * Endpoints:
  - /alluser: Handles GET requests to retrieve a list of all users in the system.
  - /delete-user/{id}: Handles DELETE requests to remove a user by their unique ID.
  - /update-user/{id}: Handles PUT requests to update a user’s information by their unique ID. */
@RestController
@RequestMapping("/api/user") // Base URL for all user-related endpoints
public class UserController {

    // Injects UserService to handle business logic for user operations
    @Autowired
    private UserService userService;

    /* Retrieves all users from the system.

       @return - ResponseEntity containing a list of all users. */
    @GetMapping("/alluser")
    public ResponseEntity<?> getAllUser() {
        return userService.getAll();
    }

    /* Deletes a user by their unique ID.

       @param id - The ID of the user to delete.
       @return - ResponseEntity with a success or error message. */
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        return userService.deleteById(id);
    }

    /* Updates a user's information by their unique ID.

       @param id - The ID of the user to update.
       @param updateUserRequest - The request containing updated user data.
       @return - ResponseEntity containing the updated user information or an error message. */
    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(id, updateUserRequest);
    }
}
