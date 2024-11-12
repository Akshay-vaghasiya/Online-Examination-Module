package com.example.backend.Controller;

import com.example.backend.Entity.University;
import com.example.backend.Service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* The UniversityController class is a REST controller responsible for handling requests related to
   university operations. It provides endpoints to register a new university, retrieve all universities,
   update an existing university, and delete a university.

 * Purpose:
  - This controller manages HTTP requests related to university data and delegates the processing
    to the UniversityService, which handles the core logic.

 * Endpoints:
  - /register-university: Handles requests to register or save a new university record.
  - /getall-university: Handles requests to retrieve all universities from the system.
  - /update-university/{id}: Handles requests to update an existing university record.
  - /delete-university/{id}: Handles requests to delete a university record by ID. */

@RestController
@RequestMapping("/api/university") // Base URL for all university-related endpoints
@CrossOrigin("*") // Allows cross-origin requests from any origin
public class UniversityController {

    // Injects UniversityService to handle business logic for university operations
    @Autowired
    private UniversityService universityService;

    /* Registers or saves a new university record in the system.

       param university - The University object to be saved.
       return - ResponseEntity with the saved university data. */
    @PostMapping("/register-university")
    public ResponseEntity<?> saveUniversity(@RequestBody University university) {
        try{
            return ResponseEntity.ok(universityService.saveUniversity(university));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while register the university.");
        }
    }

    /* Retrieves all universities from the system.

       return - ResponseEntity with a list of all universities. */
    @GetMapping("/getall-university")
    public ResponseEntity<?> getUniversities() {

        try{
            return ResponseEntity.ok(universityService.getAllUniversities());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the university.");
        }
    }

    /* Updates an existing university record by its ID.

       param id - The ID of the university to update.
       param university - The updated University object with new details.
       return - ResponseEntity indicating the success or failure of the update operation. */
    @PutMapping("/update-university/{id}")
    public ResponseEntity<?> updateUniversity(@PathVariable("id") long id, @RequestBody University university) {
        return universityService.updateUniversity(id, university);
    }

    /* Deletes a university record by its ID.

       param id - The ID of the university to delete.
       return - ResponseEntity indicating the success or failure of the delete operation. */
    @DeleteMapping("/delete-university/{id}")
    public ResponseEntity<?> deleteUniversity(@PathVariable("id") long id) {
        return universityService.deleteUniversity(id);
    }
}