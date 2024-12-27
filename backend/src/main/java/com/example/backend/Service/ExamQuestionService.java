package com.example.backend.Service;

import com.example.backend.Dto.*;
import com.example.backend.Entity.Exam;
import com.example.backend.Entity.ExamQuestion;
import com.example.backend.Entity.StudentAnswer;
import com.example.backend.Entity.StudentExam;
import com.example.backend.Repository.ExamQuestionRepository;
import com.example.backend.Repository.ExamRepository;
import com.example.backend.Repository.StudentExamRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamQuestionService {

    // Injects an instance of ExamQuestionRepository for database operations
    @Autowired
    private ExamQuestionRepository examQuestionRepository;

    // Injects an instance of ExamRepository for database operations
    @Autowired
    private ExamRepository examRepository;

    // Injects an instance of StudentExamRepository for database operations
    @Autowired
    private StudentExamRepository studentExamRepository;

    /* Retrieves MCQs for a specific exam.

       @param examId - The ID of the exam from which to retrieve questions.
       @param pageable - Pagination information for the retrieved questions.
       @param email - The email of the student.
       @return A list of ExamQuestionDTO representing the MCQ questions.
       @throws BadRequestException - If the user has not started the exam or if the exam is not in a valid state. */
    public List<ExamQuestionDTO> getMcqQuestions(Long examId, Pageable pageable, String email) throws BadRequestException {

        boolean user = examRepository.existsByStudentEmail(email);
        if(!user) {
            throw new BadRequestException("User not started exam");
        }

        Exam exam = examRepository.getReferenceById(examId);

        if((exam.getStatus() != Exam.ExamStatus.STARTED && exam.getStatus() != Exam.ExamStatus.RESUMED) || !exam.isEnable() || !exam.getScheduleDate().equals(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid data for exam");
        }

        StudentExam studentExam = studentExamRepository.findByStudentEmailAndExamId(email, examId).get();

        String duration = exam.getDuration();
        int durationint = Integer.parseInt(duration);

        LocalDateTime starttime = studentExam.getStartTime();
        LocalDateTime endtime = studentExam.getEndTime();

        if (durationint < Duration.between(starttime, endtime).toMinutes() && studentExam.isCompleted()) {
            throw new IllegalStateException("Auto-save failed because of Exam time is over.");
        }

        Page<ExamQuestion> codingQuestions = examQuestionRepository.findMcqQuestionsByExamId(examId, pageable);

        List<ExamQuestion> codingQuestionsList = codingQuestions.getContent();

        List<ExamQuestionDTO> questions = mapToExamQuestionDTO(codingQuestionsList);

        Set<StudentAnswer> answers = studentExam.getAnswers();

        for(StudentAnswer answer : answers) {

            if(answer.getCodingQuestion() != null) {

                for(ExamQuestionDTO examQuestionDTO : questions) {

                    if (examQuestionDTO.getCodingQuestion().getId() == answer.getCodingQuestion().getId()) {
                        examQuestionDTO.setAnswer(answer.getAnswer());
                        break;
                    }
                }
            } else {
                for(ExamQuestionDTO examQuestionDTO : questions) {

                    if (examQuestionDTO.getMcqQuestion().getId() == answer.getMcqQuestion().getId()) {
                        examQuestionDTO.setAnswer(answer.getAnswer());
                        break;
                    }
                }
            }
        }

        return questions;
    }

    /* Retrieves coding questions for a specific exam.

       @param examId - The ID of the exam from which to retrieve coding questions.
       @param pageable - Pagination information for the retrieved questions.
       @param email - The email of the student.
       @return A list of ExamQuestionDTOs representing the coding questions.
       @throws BadRequestException - If the user has not started the exam or if the exam is not in a valid state. */
    public List<ExamQuestionDTO> getCodingQuestions(Long examId, Pageable pageable, String email) throws BadRequestException{

        boolean user = examRepository.existsByStudentEmail(email);
        if(!user) {
            throw new BadRequestException("User not started exam");
        }

        Exam exam = examRepository.getReferenceById(examId);

        if((exam.getStatus() != Exam.ExamStatus.STARTED && exam.getStatus() != Exam.ExamStatus.RESUMED) || !exam.isEnable() || !exam.getScheduleDate().equals(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid data for exam");
        }

        StudentExam studentExam = studentExamRepository.findByStudentEmailAndExamId(email, examId).get();

        String duration = exam.getDuration();
        int durationint = Integer.parseInt(duration);

        LocalDateTime starttime = studentExam.getStartTime();
        LocalDateTime endtime = studentExam.getEndTime();

        if (durationint < Duration.between(starttime, endtime).toMinutes() && studentExam.isCompleted()) {
            throw new IllegalStateException("Auto-save failed because of Exam time is over.");
        }

        Page<ExamQuestion> codingQuestions = examQuestionRepository.findCodingQuestionsByExamId(examId, pageable);

        List<ExamQuestion> codingQuestionsList = codingQuestions.getContent();

        List<ExamQuestionDTO> questions = mapToExamQuestionDTO(codingQuestionsList);

        Set<StudentAnswer> answers = studentExam.getAnswers();

        for(StudentAnswer answer : answers) {

            if(answer.getCodingQuestion() != null) {

                for(ExamQuestionDTO examQuestionDTO : questions) {

                    if (examQuestionDTO.getCodingQuestion().getId() == answer.getCodingQuestion().getId()) {
                        examQuestionDTO.setAnswer(answer.getAnswer());
                        break;
                    }
                }
            } else {
                for(ExamQuestionDTO examQuestionDTO : questions) {

                    if (examQuestionDTO.getMcqQuestion().getId() == answer.getMcqQuestion().getId()) {
                        examQuestionDTO.setAnswer(answer.getAnswer());
                        break;
                    }
                }
            }
        }

        return questions;
    }

    /* Maps a list of ExamQuestion entities to their corresponding ExamQuestionDTO.

       @param examQuestions - A list of ExamQuestion entities to be mapped.
       @return A list of mapped ExamQuestionDTOs. */
    private List<ExamQuestionDTO> mapToExamQuestionDTO(List<ExamQuestion> examQuestions) {

        List<ExamQuestionDTO> examQuestionsList = new ArrayList<>();

        for(ExamQuestion examQuestion : examQuestions) {

            ExamQuestionDTO examQuestionDTO = new ExamQuestionDTO();

            examQuestionDTO.setMarks(examQuestion.getMarks());
            examQuestionDTO.setId(examQuestion.getId());

            if (examQuestion.getMcqQuestion() != null) {
                McqQuestionDTO mcqDto = new McqQuestionDTO();
                mcqDto.setId(examQuestion.getMcqQuestion().getId());
                mcqDto.setQuestionText(examQuestion.getMcqQuestion().getQuestionText());
                mcqDto.setCategory(examQuestion.getMcqQuestion().getCategory());


                Set<McqOptionDTO> options = examQuestion.getMcqQuestion().getOptions().stream()
                        .map(option -> new McqOptionDTO(option.getId(), option.getOptionText()))
                        .collect(Collectors.toSet());

                mcqDto.setOptions(options);

                examQuestionDTO.setMcqQuestion(mcqDto);
            } else {
                CodingQuestionDTO codeDto = new CodingQuestionDTO();
                codeDto.setCategory(examQuestion.getCodingQuestion().getCategory());
                codeDto.setId(examQuestion.getCodingQuestion().getId());
                codeDto.setQuestionText(examQuestion.getCodingQuestion().getQuestionText());
                codeDto.setFunctionSignature(examQuestion.getCodingQuestion().getFunctionSignature());

                Set<CodingTestCaseDTO> testcases = examQuestion.getCodingQuestion().getTestCases().stream()
                        .map(testcase -> new CodingTestCaseDTO(testcase.getId(), testcase.getInputData(), testcase.getExpectedOutput()))
                        .collect(Collectors.toSet());

                codeDto.setTestCases(testcases);

                examQuestionDTO.setCodingQuestion(codeDto);
            }

            examQuestionsList.add(examQuestionDTO);
        }

        return examQuestionsList;
    }
}