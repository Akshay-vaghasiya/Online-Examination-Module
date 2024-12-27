package com.example.backend.Dto;

/* The StudentAnswerDTO class is used to represent a student's response to a question in an exam.
   This class provides details about the question being answered, its type, and the student's answer.

 * Fields:
   - questionId: Represents the unique identifier of the question that the student is answering.
   - questionType: Specifies the type of the question.
   - answer: Contains the student's answer to the question. For MCQs, it may represent the selected option, and
     for coding questions, it contains the code.
   - language: Indicates the programming language used by the student for coding questions otherwise it will be null.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of the student's answer details. */
public class StudentAnswerDTO {
    private Long questionId;
    private String questionType;
    private String answer;
    private String language;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
