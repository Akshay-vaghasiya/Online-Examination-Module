package com.example.backend.Service;

import com.example.backend.Dto.StudentExamDisplay;
import com.example.backend.Entity.*;
import com.example.backend.Repository.*;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentExamService {

    // Injects an instance of UserService for database operations
    @Autowired
    private UserService userService;

    // Injects an instance of ExamRepository for database operations
    @Autowired
    private ExamRepository examRepository;

    // Injects an instance of StudentExamRepository for database operations
    @Autowired
    private StudentExamRepository studentExamRepository;

    // Injects an instance of ExamResultRepository for database operations
    @Autowired
    private ExamResultRepository examResultRepository;

    /* Finds all eligible exams for a student based on their university, branch, and semester.

      @param email - The email of the student.
      @return A list of eligible exams for the student.
      @throws EntityNotFoundException - If the user or university is not found. */
    public List<StudentExamDisplay> findExam(String email) {

        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with this email");
        }

        University university = user.getUniversity();
        if (university == null) {
            throw new EntityNotFoundException("University not found for the user");
        }

        List<Exam> exams = examRepository.findExamsByUniversityId(university.getUniversityId());

        return exams.stream()
                .filter(exam -> {
                    if (exam.isEnable() && exam.getBranch().equalsIgnoreCase(user.getBranch()) && exam.getSemester() == user.getSemester()) {
                        Optional<StudentExam> studentExam = studentExamRepository.findByStudentEmailAndExamId(email, exam.getId());
                        if (!studentExam.isPresent()) return true;

                        String duration = exam.getDuration();
                        int durationInt = Integer.parseInt(duration);

                        LocalDateTime startTime = studentExam.get().getStartTime();
                        LocalDateTime endTime = studentExam.get().getEndTime();

                        return (durationInt > Duration.between(startTime, endTime).toMinutes() && !studentExam.get().isCompleted());
                    }

                    return false;
                })
                .map(exam -> {
                    StudentExamDisplay examDisplay = new StudentExamDisplay();
                    examDisplay.setExamName(exam.getExamName());
                    examDisplay.setId(exam.getId());
                    examDisplay.setDuration(exam.getDuration());
                    examDisplay.setEnable(exam.isEnable());
                    examDisplay.setStatus(exam.getStatus());
                    return examDisplay;
                })
                .collect(Collectors.toList());
    }

    /* Creates a studentExam record for a specific exam and student.

       @param email - The email of the student.
       @param examId - The ID of the exam.
       @return The created or existing StudentExam record.
       @throws EntityNotFoundException - If the user or exam is not found.
       @throws IllegalArgumentException - If the exam or user data is invalid. */
    public StudentExam createExam(String email, long examId) {

        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with this email");
        }

        Exam exam = examRepository.getReferenceById(examId);
        if (exam == null) {
            throw new EntityNotFoundException("Exam not found with this examId");
        }

        if (exam.getSemester() == 0 || user.getSemester() == 0 || exam.getBranch() == null || user.getBranch() == null) {
            throw new IllegalArgumentException("Invalid data for exam or user branch/semester");
        }

        if(exam.getSemester() != user.getSemester() && !exam.getBranch().equals(user.getBranch())) {
            throw new IllegalArgumentException("Not fulfill creation criteria");
        }

        if(!exam.isEnable() || (exam.getStatus() != Exam.ExamStatus.STARTED && exam.getStatus() != Exam.ExamStatus.PAUSED) || !exam.getScheduleDate().equals(LocalDate.now())) {
            throw new IllegalArgumentException("Not fulfill creation criteria");
        }

        Optional<StudentExam> existingExam = studentExamRepository.findByStudentEmailAndExamId(email, examId);

        if (existingExam.isPresent()) {
            return existingExam.get();
        }

        StudentExam studentExam = new StudentExam();
        studentExam.setStudent(user);
        studentExam.setExam(exam);
        studentExam.setCompleted(false);
        studentExam.setStartTime(LocalDateTime.now());
        studentExam.setEndTime(LocalDateTime.now());

        return studentExamRepository.save(studentExam);
    }

    /* Submit a student's exam and set as completed and updates the end time.

       @param id - The ID of the student exam.
       @return A success message indicating the exam was submitted.
       @throws EntityNotFoundException - If the StudentExam is not found. */
    public String submitExam(long id) {

        StudentExam studentExam = studentExamRepository.getReferenceById(id);
        if(studentExam == null) {
            throw new EntityNotFoundException("Student Exam not found");
        }
        studentExam.setCompleted(true);
        studentExam.setEndTime(LocalDateTime.now());
        studentExamRepository.save(studentExam);

        ExamResult examResult = new ExamResult();
        examResult.setExam(studentExam.getExam());
        examResult.setStudent(studentExam.getStudent());

        int marks = 0;
        for(StudentAnswer answer : studentExam.getAnswers()) {

            if(answer.getMcqQuestion() != null && answer.isCorrect()) {

                if(answer.getMcqQuestion().getDifficultyLevel().equalsIgnoreCase("Easy")) {
                    marks += 1;
                } else if (answer.getMcqQuestion().getDifficultyLevel().equalsIgnoreCase("Medium")) {
                    marks += 2;
                } else {
                    marks += 3;
                }


            } else if (answer.getCodingQuestion() != null && answer.isCorrect()){

                if(answer.getCodingQuestion().getDifficultyLevel().equalsIgnoreCase("Easy")) {
                    marks += 20;
                } else if (answer.getCodingQuestion().getDifficultyLevel().equalsIgnoreCase("Medium")) {
                    marks += 30;
                } else {
                    marks += 40;
                }
            }
        }

        if(marks >= studentExam.getExam().getPassingMarks()) {
            examResult.setPassed(true);
        }
        examResult.setMarksObtained(marks);

        examResultRepository.save(examResult);

        return "Successfully exam submited";
    }

}
