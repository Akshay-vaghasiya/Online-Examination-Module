package com.example.backend.Controller;

import com.example.backend.Dto.ExamCreateRequest;
import com.example.backend.Entity.Exam;
import com.example.backend.Service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* The ExamController class is a REST controller responsible for handling requests related to exam operations.
   It provides endpoints to add, retrieve, update, and delete exams from the system.

 * Purpose:
  - This controller manages HTTP requests related to exam data and delegates the processing to the ExamService,
    which handles the core logic.

 * Endpoints:
  - /create-exam: Create an exam in the system.
  - /exams: Retrieves all the exams from the system.
  - /update-exam/{id}: Updates an existing exam data by ID.
  - /delete-exam/{id}: Deletes an exam by ID. */
@RestController
@RequestMapping("/api/exam")
public class ExamController {

    // Injects ExamService to handle business logic for exam operations
    @Autowired
    ExamService examService;

    /* Create an exam into the system

       @param examCreateRequest - For data to create an exam, and it is a dto object.
       @eeturn - ResponseEntity with the created exam or error message in case of failure. */
    @PostMapping("/create-exam")
    public ResponseEntity<?> createExam(@RequestBody ExamCreateRequest examCreateRequest) {
        try {
            Exam exam = examService.createExam(examCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(exam);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

    /* Retrives all the exams from the system

       @return - ResponseEntity with list of exams in the system or error message in case of failure. */
    @GetMapping("/exams")
    public ResponseEntity<?> getExams() {
        try {
            List<Exam> exams = examService.getExams();
            return ResponseEntity.status(HttpStatus.CREATED).body(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

    /* Updates an existing exam data by ID

       @param id - For find exam in system.
       @param examCreateRequest - For update existing exam data, and it is a dto object.
       @return - ResponseEntity with updated exam or error message in case of failure. */
    @PutMapping("/update-exam/{id}")
    public ResponseEntity<?> updateExam(@PathVariable long id, @RequestBody ExamCreateRequest examCreateRequest) {
        try {
            Exam exam1 = examService.updateExam(id, examCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(exam1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

    /* Deletes an exam by ID.

       @param id - for delete exam which contain this id.
       @return - ResponseEntity with success message or error message in case of failure. */
    @DeleteMapping("/delete-exam/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable long id) {
        try {
            examService.deleteExam(id);
            return ResponseEntity.status(HttpStatus.CREATED).body("Delete exam successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong.");
        }
    }
}