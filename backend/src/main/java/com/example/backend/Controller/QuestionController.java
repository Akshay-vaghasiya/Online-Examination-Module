package com.example.backend.Controller;

import com.example.backend.Entity.CodingQuestion;
import com.example.backend.Entity.McqQuestion;
import com.example.backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* The QuestionController class is a REST controller responsible for handling requests related to question operations.
   It provides endpoints to add MCQ and coding questions to the system.

 * Purpose:
  - This controller manages HTTP requests related to question data (both MCQ and coding questions) and delegates
    the processing to the QuestionService, which handles the core logic.

 * Endpoints:
  - /add-mcq: Handles requests to add a new MCQ question to the system.
  - /add-coding: Handles requests to add a new coding question to the system. */
@RestController
@RequestMapping("/api/questions") // Base URL for all question-related endpoints
public class QuestionController {

    // Injects QuestionService to handle business logic for question operations
    @Autowired
    private QuestionService questionService;

    /* Adds a new MCQ question to the system.

       param question - The MCQ question to be added.
       return - ResponseEntity with the saved MCQ question data, or error message in case of failure. */
    @PostMapping("/add-mcq")
    public ResponseEntity<?> addMcqQuestion(@RequestBody McqQuestion question) {

        try {
            McqQuestion savedQuestion = questionService.addMcqQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while adding the MCQ question.");
        }
    }

    /* Adds a new coding question to the system.

       param question - The coding question to be added.
       return - ResponseEntity with the saved coding question data, or error message in case of failure. */
    @PostMapping("/add-coding")
    public ResponseEntity<?> addCodingQuestion(@RequestBody CodingQuestion question) {

        try {
            CodingQuestion savedQuestion = questionService.addCodingQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while adding the coding question.");
        }
    }
}