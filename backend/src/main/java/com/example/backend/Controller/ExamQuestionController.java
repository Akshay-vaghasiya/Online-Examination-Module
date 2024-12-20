package com.example.backend.Controller;

import com.example.backend.Service.ExamQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* The ExamQuestionController class is responsible for handling requests related to exam questions.
   It provides endpoints for retrieving paginated MCQ and coding questions assigned to a specific student for a given exam.

 * Endpoints:
   - /{exam_id}/mcq/{email}: Retrieves paginated MCQ questions for a specific exam and student.
   - /{exam_id}/code/{email}: Retrieves paginated coding questions for a specific exam and student. */
@RestController
@RequestMapping("/api/exam-questions")
public class ExamQuestionController {

    // Injects ExamQuestionService to handle business logic for exam question operations.
    @Autowired
    private ExamQuestionService examQuestionService;

    /* Retrieves paginated MCQ questions for a specific exam and student.

       @param exam_id - The unique identifier of the exam.
       @param email - The email address of the student.
       @param page - The page number of the paginated results.
       @param size - The number of questions per page.
       @return - ResponseEntity containing the paginated list of MCQ questions or an error message in case of failure. */
    @GetMapping("/{exam_id}/mcq/{email}")
    public ResponseEntity<?> getMcqQuestions(@PathVariable Long exam_id,
                                             @PathVariable String email,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(examQuestionService.getMcqQuestions(exam_id, pageable, email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong in pagination");
        }
    }

    /* Retrieves paginated coding questions for a specific exam and student.

       @param exam_id - The unique identifier of the exam.
       @param email - The email address of the student.
       @param page - The page number of the paginated results.
       @param size - The number of questions per page.
       @return - ResponseEntity containing the paginated list of coding questions or an error message in case of failure. */
    @GetMapping("/{exam_id}/code/{email}")
    public ResponseEntity<?> getCodingQuestions(@PathVariable Long exam_id,
                                                @PathVariable String email,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(examQuestionService.getCodingQuestions(exam_id, pageable, email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong in pagination");
        }
    }
}

