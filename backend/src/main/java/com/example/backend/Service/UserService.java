package com.example.backend.Service;

import com.example.backend.Dto.UpdateUserRequest;
import com.example.backend.Dto.UserResponse;
import com.example.backend.Entity.University;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Injects an instance of UserRepository for database operations on User entities.
    @Autowired
    private UserRepository userRepository;

    // Injects an instance of UniversityService to access university-related data.
    @Autowired
    private UniversityService universityService;

    /* Saves or updates a User entity in the database.

       @param user - The User object to be saved or updated.
    */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /* Retrieves all users from the database and returns them in a list of UserResponse objects.

       @return - A ResponseEntity containing a list of UserResponse objects if successful, or an error message otherwise.
    */
    public ResponseEntity<?> getAll() {
        try {
            List<User> users = userRepository.findAll();
            List<UserResponse> userResponses = new ArrayList<>();

            for (User user : users) {
                UserResponse userResponse = new UserResponse();
                userResponse.setUserid(user.getUserId());
                userResponse.setRole(user.getRole());
                userResponse.setEmail(user.getEmail());
                userResponse.setUsername(user.getUsername());
                userResponse.setBranch(user.getBranch());
                userResponse.setSemester(user.getSemester());
                if (user.getUniversity() != null) {
                    userResponse.setUniversity(user.getUniversity().getUniversityName());
                }
                userResponses.add(userResponse);
            }

            return ResponseEntity.ok(userResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the users.");
        }
    }

    /* Finds a user by their unique ID.

       @param id - The ID of the user to retrieve.
       @return - An Optional containing the User object if found, or empty if not found.
    */
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    /* Deletes a user by their unique ID.

       @param id - The ID of the user to delete.
       @return - A ResponseEntity with a success message if the user is deleted, or an error message if not found.
    */
    public ResponseEntity<?> deleteById(long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("User deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the user.");
        }
    }

    /* Finds a user by their email address.

       @param email - The email address of the user to retrieve.
       @return - The User object if found, or null if no user is found with the specified email.
    */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /* Updates a user's information based on the provided UpdateUserRequest data.

       @param id - The ID of the user to update.
       @param updateUserRequest - The request containing the updated user information.
       @return - A ResponseEntity with the updated UserResponse if successful, or an error message otherwise.
    */
    public ResponseEntity<?> updateUser(long id, UpdateUserRequest updateUserRequest) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                if (updateUserRequest.getEmail() != null && !user.getEmail().equals(updateUserRequest.getEmail())) {
                    user.setEmail(updateUserRequest.getEmail());
                }
                if (updateUserRequest.getBranch() != null && !user.getBranch().equals(updateUserRequest.getBranch())) {
                    user.setBranch(updateUserRequest.getBranch());
                }
                if (updateUserRequest.getSemester() <= 8 && updateUserRequest.getSemester() >= 1 && user.getSemester() != updateUserRequest.getSemester()) {
                    user.setSemester(updateUserRequest.getSemester());
                }
                if (updateUserRequest.getUsername() != null && !user.getUsername().equals(updateUserRequest.getUsername())) {
                    user.setUsername(updateUserRequest.getUsername());
                }
                if (updateUserRequest.getRole() != null && !user.getRole().equals(updateUserRequest.getRole())) {
                    user.setRole(updateUserRequest.getRole());
                }
                if (updateUserRequest.getRole() == User.Role.STUDENT && updateUserRequest.getUniversity() != null && !updateUserRequest.getUniversity().isEmpty() &&
                        (user.getUniversity() == null || !user.getUniversity().getUniversityName().equals(updateUserRequest.getUniversity()))) {

                    University university = universityService.getUniversityByName(updateUserRequest.getUniversity());
                    if (university != null) {
                        user.setUniversity(university);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("University not found.");
                    }
                }

                userRepository.save(user);
                UserResponse updatedUser = new UserResponse();
                updatedUser.setUniversity(user.getUniversity() != null ? user.getUniversity().getUniversityName() : null);
                updatedUser.setUserid(user.getUserId());
                updatedUser.setUsername(user.getUsername());
                updatedUser.setRole(user.getRole());
                updatedUser.setEmail(user.getEmail());
                updatedUser.setSemester(user.getSemester());
                updatedUser.setBranch(user.getBranch());
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the user.");
        }
    }
}
