package com.example.backend.Service;

import com.example.backend.Entity.University;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UniversityRepository;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {

    // Injects an instance of UniversityRepository for database operations
    @Autowired
    private UniversityRepository universityRepository;

    // Injects an instance of UserRepository for database operations
    @Autowired
    private UserRepository userRepository;

    /* Saves a new university or updates an existing university in the database.

      @param university - The University object to save.
      @return The saved University object. */
    public University saveUniversity(University university) {
        return universityRepository.save(university);
    }

    /* Retrieves a list of all universities from the database.

      @return A list of all universities. */
    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    /* Finds a university by its unique ID.

      @param universityId - The ID of the university to find.
      @return An Optional containing the university if found, otherwise empty. */
    public Optional<University> getUniversityById(Long universityId) {
        return universityRepository.findById(universityId);
    }

    /* Finds a university by its name.

      @param universityName - The name of the university to find.
      @return The University object if found, otherwise null. */
    public University getUniversityByName(String universityName) {
        return universityRepository.findByUniversityName(universityName);
    }

    /* Updates an existing university's details by its ID.

      @param universityId - The ID of the university to update.
      @param updatedUniversity - The University object containing updated details.
      @return A ResponseEntity containing the updated University object if found, or an error message if
      the university is not found or another error occurs. */
    public ResponseEntity<?> updateUniversity(long universityId, University updatedUniversity) {
        try {
            Optional<University> optionalUniversity = getUniversityById(universityId);

            if (optionalUniversity.isPresent()) {
                University university = optionalUniversity.get();
                university.setUniversityName(updatedUniversity.getUniversityName());
                university.setAddress(updatedUniversity.getAddress());
                university.setContactEmail(updatedUniversity.getContactEmail());
                university.setContactPhone(updatedUniversity.getContactPhone());
                university.setWebsiteUrl(updatedUniversity.getWebsiteUrl());

                universityRepository.save(university);

                // Retrieve and return the updated university
                University university1 = getUniversityById(universityId).get();
                return ResponseEntity.ok(university1);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("University not found.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the university.");
        }
    }

    /* Deletes a university by its unique ID.

      @param universityId - The ID of the university to delete.
      @return A ResponseEntity indicating the success of the deletion, or an error message if the
      university is not found or another error occurs. */
    public ResponseEntity<?> deleteUniversity(long universityId) {
        try {
            if (universityRepository.existsById(universityId)) {

                University university = universityRepository.findById(universityId).get();
                List<User> users = userRepository.findAll();

                for(User user : users){
                    if(user.getUniversity() == university){
                        user.setUniversity(null);
                        userRepository.save(user);
                    }
                }
                universityRepository.deleteById(universityId);
                return ResponseEntity.ok("University deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("University not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the university.");
        }
    }
}