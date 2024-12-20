package com.example.backend.Controller;

import com.example.backend.Dto.AutoSaveRequest;
import com.example.backend.Dto.CodeRequest;
import com.example.backend.Dto.StudentExamDisplay;
import com.example.backend.Service.StudentExamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* The StudentExamController class is responsible for handling requests related to studentExam operations.
   It provides endpoints for students to find available exams, start exams, submit exams.

 * Endpoints:
  - /find-exam/{email}: Retrieves all exams assigned to a student identified by their email.
  - /create-exam/{email}/{exam_id}: Starts an exam for a student by their email and exam ID.
  - /submit-exam/{id}: Allows a student to submit an exam by its unique ID. */
@RestController
@RequestMapping("/api/student-exam")
public class StudentExamController {

    // Injects StudentExamService to handle business logic for studentExam operations.
    @Autowired
    private StudentExamService studentExamService;

    /* Retrieves all exams assigned to a student based on their email.

       @param email - The email address of the student.
       @return - ResponseEntity with a list of StudentExamDisplay objects representing the exams or an error message in case of failure. */
    @GetMapping("/find-exam/{email}")
    public ResponseEntity<?> findExam(@PathVariable String email) {
        try {
            List<StudentExamDisplay> examDisplayList = studentExamService.findExam(email);
            return ResponseEntity.status(HttpStatus.OK).body(examDisplayList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

    /* Assigns or starts an exam for a student.

       @param email - The email address of the student.
       @param exam_id - The unique identifier of the exam to be a started.
       @return - ResponseEntity with confirmation of exam creation or an error message in case of failure. */
    @PostMapping("/create-exam/{email}/{exam_id}")
    public ResponseEntity<?> createExam(@PathVariable String email, @PathVariable long exam_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(studentExamService.createExam(email, exam_id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong exam not created");
        }
    }

    /* Submits an exam after the student completes it and auto-submit exam.

       @param id - The unique identifier of the student exam being submitted.
       @return - ResponseEntity with confirmation of successful submission or an error message in case of failure. */
    @PutMapping("/submit-exam/{id}")
    public ResponseEntity<?> submitExam(@PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(studentExamService.submitExam(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong in submit exam");
        }
    }
}
