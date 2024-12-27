package com.example.backend.Dto;

import java.util.List;

/* The AutoSaveRequest class is used to represent the data required for auto-saving.
   This class provides details about the student exam id and the answers they have provided.

 * Fields:
   - studentExamId: Represents the unique identifier of the student exam.
   - answers: Contains a list of StudentAnswerDTO objects, where each object contains a student's response
     to a particular question in the exam.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of the auto-save request details. */
public class AutoSaveRequest {
    private Long studentExamId;
    private List<StudentAnswerDTO> answers;

    public Long getStudentExamId() {
        return studentExamId;
    }

    public void setStudentExamId(Long studentExamId) {
        this.studentExamId = studentExamId;
    }

    public List<StudentAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<StudentAnswerDTO> answers) {
        this.answers = answers;
    }
}
