package com.example.backend.Controller;

import com.example.backend.Entity.CodingQuestion;
import com.example.backend.Entity.McqQuestion;
import com.example.backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* The QuestionController class is a REST controller responsible for handling requests related to question operations.
   It provides endpoints to add, retrieve, update, and delete MCQ and coding questions from the system.

 * Purpose:
  - This controller manages HTTP requests related to question data (both MCQ and coding questions) and delegates
    the processing to the QuestionService, which handles the core logic.

 * Endpoints:
  - /add-mcq: Handles requests to add a new MCQ question to the system.
  - /add-coding: Handles requests to add a new coding question to the system.
  - /get-mcqs: Retrieves all MCQ questions from the system.
  - /get-codes: Retrieves all coding questions from the system.
  - /update-mcq/{id}: Updates an existing MCQ question by ID.
  - /update-coding/{id}: Updates an existing coding question by ID.
  - /delete-mcq/{id}: Deletes an MCQ question by ID.
  - /delete-coding/{id}: Deletes a coding question by ID. */
@RestController
@RequestMapping("/api/questions") // Base URL for all question-related endpoints
public class QuestionController {

    // Injects QuestionService to handle business logic for question operations
    @Autowired
    private QuestionService questionService;

    /* Adds a new MCQ question to the system.

       @param question - The MCQ question to be added.
       @return - ResponseEntity with the saved MCQ question data, or error message in case of failure. */
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

       @param question - The coding question to be added.
       @return - ResponseEntity with the saved coding question data, or error message in case of failure. */
    @PostMapping("/add-coding")
    public ResponseEntity<?> addCodingQuestion(@RequestBody CodingQuestion question) {

        try {
            CodingQuestion savedQuestion = questionService.addCodingQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while adding the coding question.");
        }
    }

    /* Retrieves all MCQ questions from the system.

       @return - ResponseEntity with a list of all MCQ questions, or error message in case of failure. */
    @GetMapping("/get-mcqs")
    public ResponseEntity<?> getMcqs() {
        try {
            List<McqQuestion> mcqs = questionService.getMcqQuestion();
            return ResponseEntity.status(HttpStatus.OK).body(mcqs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    /* Retrieves all coding questions from the system.

       @return - ResponseEntity with a list of all coding questions, or error message in case of failure. */
    @GetMapping("/get-codes")
    public ResponseEntity<?> getCondingQuestions() {
        try {
            List<CodingQuestion> codes = questionService.getCodingQuestion();
            return ResponseEntity.status(HttpStatus.OK).body(codes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    /* Updates an existing MCQ question in the system.

       @param id - The ID of the MCQ question to be updated.
       @param updatedQuestion - The updated MCQ question data.
       @return - ResponseEntity with the updated MCQ question data, or error message in case of failure. */
    @PutMapping("/update-mcq/{id}")
    public ResponseEntity<?> updateMcqQuestion(@PathVariable long id, @RequestBody McqQuestion updatedQuestion) {
        try {
            McqQuestion question = questionService.updateMcqQuestion(id, updatedQuestion);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    /* Updates an existing coding question in the system.

       @param id - The ID of the coding question to be updated.
       @param updatedQuestion - The updated coding question data.
       @return - ResponseEntity with the updated coding question data, or error message in case of failure. */
    @PutMapping("/update-coding/{id}")
    public ResponseEntity<?> updateCodingQuestion(@PathVariable long id, @RequestBody CodingQuestion updatedQuestion) {
        try {
            CodingQuestion question = questionService.updateCodingQuestion(id, updatedQuestion);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    /* Deletes an existing MCQ question from the system.

       @param id - The ID of the MCQ question to be deleted.
       @return - ResponseEntity indicating success or error message in case of failure. */
    @DeleteMapping("/delete-mcq/{id}")
    public ResponseEntity<?> deleteMcqQuestion(@PathVariable long id) {
        try {
            questionService.deleteMcqQuestion(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("MCQ question deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    /* Deletes an existing coding question from the system.

       @param id - The ID of the coding question to be deleted.
       @return - ResponseEntity indicating success or error message in case of failure. */
    @DeleteMapping("/delete-coding/{id}")
    public ResponseEntity<?> deleteCodingQuestion(@PathVariable long id) {
        try {
            questionService.deleteCodingQuestion(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Coding question deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}