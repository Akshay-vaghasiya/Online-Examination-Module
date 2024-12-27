package com.example.backend.Service;

import com.example.backend.Dto.AutoSaveRequest;
import com.example.backend.Dto.StudentAnswerDTO;
import com.example.backend.Dto.StudentExamDisplay;
import com.example.backend.Entity.*;
import com.example.backend.Repository.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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

    // Injects an instance of StudentAnswerRepository for database operations
    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    // Injects an instance of CodingQuestionRepository for database operations
    @Autowired
    private CodingQuestionRepository codingQuestionRepository;

    // Injects an instance of McqQuestionRepository for database operations
    @Autowired
    private McqQuestionRepository mcqQuestionRepository;

    // Data fields of this class
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    Map<String, Integer> languagemap = new HashMap<>();

    // Reads the judge0.api.key from environment variables
    @Value("${judge0.api.key}")
    private String apiKey;

    // Create constructor with arguments webClientBuilder and objectMapper
    @Autowired
    public StudentExamService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("https://judge0-ce.p.rapidapi.com").build();
        this.objectMapper = objectMapper;

        languagemap.put("C++", 105);
        languagemap.put("Java", 91);
        languagemap.put("Javascript", 102);
        languagemap.put("Python", 71);
        languagemap.put("C", 103);
    }

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

    /* Saves student answers for a specific exam.

   @param examId - The ID of the exam.
   @param autoSaveRequest - The auto-save request containing answers to save.
   @throws RuntimeException - If the StudentExam or Exam is not found.
   @throws IllegalStateException - If the exam time is over. */
    public void autoSaveAnswers(long examId, AutoSaveRequest autoSaveRequest) {

        StudentExam studentExam = studentExamRepository.findById(autoSaveRequest.getStudentExamId())
                .orElseThrow(() -> new RuntimeException("StudentExam not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        String duration = exam.getDuration();
        int durationint = Integer.parseInt(duration);

        LocalDateTime starttime = studentExam.getStartTime();
        LocalDateTime endtime = studentExam.getEndTime();

        if (durationint < Duration.between(starttime, endtime).toMinutes()) {
            throw new IllegalStateException("Auto-save failed because of Exam time is over.");
        }

        for (StudentAnswerDTO answerDTO : autoSaveRequest.getAnswers()) {
            StudentAnswer studentAnswer = getOrCreateStudentAnswer(studentExam, answerDTO);
            studentAnswer.setAnswer(answerDTO.getAnswer());
            studentAnswer.setLanguage(answerDTO.getLanguage());

            if (answerDTO.getQuestionType().equalsIgnoreCase("mcq")) {

                List<McqOption> answer = (studentAnswer.getMcqQuestion().getOptions().stream().filter(ans -> ans.getisCorrect())).collect(Collectors.toList());
                if(answer.get(0).getOptionText().equalsIgnoreCase(answerDTO.getAnswer())) {
                    studentAnswer.setCorrect(true);
                } else {
                    studentAnswer.setCorrect(false);
                }

            }

            studentAnswerRepository.save(studentAnswer);
        }

        studentExam.setEndTime(LocalDateTime.now());
        studentExamRepository.save(studentExam);
    }

    /* Retrieves or creates a StudentAnswer record for the provided question and student exam.

       @param studentExam - The student exam to associate the answer with.
       @param answerDTO - The DTO containing answer data.
       @return The retrieved or newly created StudentAnswer. */
    private StudentAnswer getOrCreateStudentAnswer(StudentExam studentExam, StudentAnswerDTO answerDTO) {

        if (answerDTO.getQuestionType().equalsIgnoreCase("mcq")) {
            return studentAnswerRepository.findByStudentExamAndMcqQuestionId(studentExam, answerDTO.getQuestionId())
                    .orElseGet(() -> {
                        StudentAnswer newAnswer = new StudentAnswer();
                        newAnswer.setStudentExam(studentExam);
                        McqQuestion mcqQuestion = mcqQuestionRepository.findById(answerDTO.getQuestionId())
                                .orElseThrow(() -> new RuntimeException("MCQ Question not found"));
                        newAnswer.setMcqQuestion(mcqQuestion);
                        return newAnswer;
                    });
        } else if (answerDTO.getQuestionType().equalsIgnoreCase("coding")) {
            return studentAnswerRepository.findByStudentExamAndCodingQuestionId(studentExam, answerDTO.getQuestionId())
                    .orElseGet(() -> {
                        StudentAnswer newAnswer = new StudentAnswer();
                        newAnswer.setStudentExam(studentExam);
                        CodingQuestion codingQuestion = codingQuestionRepository.findById(answerDTO.getQuestionId())
                                .orElseThrow(() -> new RuntimeException("Coding Question not found"));
                        newAnswer.setCodingQuestion(codingQuestion);
                        return newAnswer;
                    });
        } else {
            throw new IllegalArgumentException("Invalid question type: " + answerDTO.getQuestionType());
        }

    }

    /* Executes a given source code on the Judge0 API and retrieves the result.

       @param sourceCode - The source code to execute.
       @param languageId - The ID of the programming language.
       @param stdin - The input for the program.
       @return The result of the code execution.
       @throws IllegalArgumentException - If the source code or language ID is invalid.
       @throws RuntimeException - If the API call or response processing fails. */
    public Object runCode(String sourceCode, int languageId, String stdin) {
        if (sourceCode == null || sourceCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Source code cannot be null or empty");
        }

        if (languageId <= 0) {
            throw new IllegalArgumentException("Invalid language ID: " + languageId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");
        headers.set("X-RapidAPI-Key", apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("source_code", sourceCode);
        body.put("language_id", languageId);
        body.put("stdin", stdin);

        try {

            String response = webClient.post()
                    .uri("/submissions?base64_encoded=false&wait=true&field=*")
                    .headers(httpHeaders -> httpHeaders.putAll(headers))
                    .bodyValue(body)
                    .exchangeToMono(clientResponse -> {
                        if (clientResponse.statusCode().is2xxSuccessful()) {
                            return clientResponse.bodyToMono(String.class);
                        } else {
                            return clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new RuntimeException("API call failed " + errorBody)));
                        }
                    })
                    .block();

            return objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});
        } catch (WebClientResponseException e) {
            System.err.println("API call failed " + e.getResponseBodyAsString());
            throw new RuntimeException("API call failed " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("Error processing response " + e.getMessage());
            throw new RuntimeException("Error processing response " + e.getMessage(), e);
        }
    }

    /* Executes a given answer code on the Judge0 API and retrieves the result and submit that if output is correct.

       @param email - The email of the student.
       @param examId - The ID of the exam.
       @param studentAnswerDTO - A DTO containing the answers of code.
       @return string message based code output.
       @throws IllegalArgumentException - If the student code or language is invalid.
       @throws EntityNotFoundException - If the Exam or CodingQuestion is not found. */
    public String submitCode(String email, long examId, StudentAnswerDTO studentAnswerDTO) {

        if (studentAnswerDTO.getAnswer() == null || studentAnswerDTO.getAnswer().trim().isEmpty()) {
            throw new IllegalArgumentException("Source code cannot be null or empty");
        }

        if ((studentAnswerDTO.getLanguage() == null) || studentAnswerDTO.getLanguage().trim().isEmpty() || (languagemap.get(studentAnswerDTO.getLanguage()) == null)) {
            throw new IllegalArgumentException("Invalid language " + studentAnswerDTO.getLanguage());
        }

        StudentExam studentExam = studentExamRepository.findByStudentEmailAndExamId(email, examId).orElseThrow(
                () -> new EntityNotFoundException("studentExam not found")
        );

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found"));

        int durationint = Integer.parseInt(exam.getDuration());
        LocalDateTime starttime = studentExam.getStartTime();
        LocalDateTime endtime = studentExam.getEndTime();

        if (durationint < Duration.between(starttime, endtime).toMinutes() && studentExam.isCompleted()) {
            throw new IllegalStateException("Code submission failed because of Exam time is over.");
        }

        CodingQuestion codingQuestion = codingQuestionRepository.findById(studentAnswerDTO.getQuestionId()).orElseThrow(
                () -> new EntityNotFoundException("Coding Question not found")
        );

        Set<CodingTestCase> codingTestCases = codingQuestion.getTestCases();

        String stdin = codingTestCases.size() + "\n";
        for (CodingTestCase codingTestCase : codingTestCases) {
            stdin += codingTestCase.getInputData() + "\n";
        }

        Map<String, Object> response = (Map<String, Object>) runCode(studentAnswerDTO.getAnswer(), languagemap.get(studentAnswerDTO.getLanguage()), stdin);

        if (response.get("stderr") != null || response.get("error") != null) {
            return "Your code gives an error.";
        }

        String output = "";
        for (CodingTestCase codingTestCase : codingTestCases) {
            output += codingTestCase.getExpectedOutput();
            if (!codingTestCase.getExpectedOutput().equals("")) {
                output += " ";
            }
            output += "\n";
        }

        if (response.get("stdout") != null && response.get("stdout").equals(output)) {
            StudentAnswer answer = studentAnswerRepository.findByStudentExamAndCodingQuestionId(studentExam, studentAnswerDTO.getQuestionId())
                    .orElseGet(() -> {
                        StudentAnswer newAnswer = new StudentAnswer();
                        newAnswer.setStudentExam(studentExam);
                        CodingQuestion codingQuestion1 = codingQuestionRepository.findById(studentAnswerDTO.getQuestionId())
                                .orElseThrow(() -> new RuntimeException("Coding Question not found"));
                        newAnswer.setCodingQuestion(codingQuestion1);
                        return newAnswer;
                    });

            answer.setCorrect(true);
            answer.setStudentExam(studentExam);
            answer.setAnswer(studentAnswerDTO.getAnswer());
            answer.setLanguage(studentAnswerDTO.getLanguage());

            studentAnswerRepository.save(answer);
            studentExam.setEndTime(LocalDateTime.now());
            studentExamRepository.save(studentExam);

            return "Your code is correct and submitted successfully";
        } else {
            return "Output does not match the expected output. Your output: " + response.get("stdout");
        }
    }

}
