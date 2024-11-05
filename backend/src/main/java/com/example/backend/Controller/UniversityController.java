package com.example.backend.Controller;

import com.example.backend.Entity.University;
import com.example.backend.Service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* The UniversityController class is a REST controller responsible for handling requests related to
   university operations. It provides endpoints to register a new university and to retrieve all universities.

 * Purpose:
  - This controller manages HTTP requests related to university data and delegates the processing
    to the UniversityService, which handles the core logic.

 * Endpoints:
  - /register-university: Handles requests to register or save a new university record.
  - /getall-university: Handles requests to retrieve all universities from the system. */
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
        return ResponseEntity.ok(universityService.saveUniversity(university));
    }

    /* Retrieves all universities from the system.

       return - ResponseEntity with a list of all universities. */
    @GetMapping("/getall-university")
    public ResponseEntity<?> getUniversities() {
        return ResponseEntity.ok(universityService.getAllUniversities());
    }
}

