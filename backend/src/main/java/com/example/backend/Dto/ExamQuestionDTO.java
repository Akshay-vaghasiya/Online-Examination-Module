package com.example.backend.Dto;

/* The ExamQuestionDTO class is used to give the details of a question attached with an exam.
   This class provides a structure to represent both MCQ and coding questions within an exam, and with the marks assigned to the question.

 * Fields:
   - id: Represents the unique identifier for the question.
   - mcqQuestion: Contains the details of MCQ question which represented in the `McqQuestionDTO` class.
   - codingQuestion: Contains the details of a coding question which represented in the `CodingQuestionDTO` class.
   - marks: Contains the marks assigned to this question in the exam.
   - answer: Contains the answer given by student in past for that question.
   - language: Contains coding question language otherwise null.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of question details. */
public class ExamQuestionDTO {

    private Long id;
    private McqQuestionDTO mcqQuestion;
    private CodingQuestionDTO codingQuestion;
    private int marks;
    private String answer;
    private String language;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public McqQuestionDTO getMcqQuestion() {
        return mcqQuestion;
    }

    public void setMcqQuestion(McqQuestionDTO mcqQuestion) {
        this.mcqQuestion = mcqQuestion;
    }

    public CodingQuestionDTO getCodingQuestion() {
        return codingQuestion;
    }

    public void setCodingQuestion(CodingQuestionDTO codingQuestion) {
        this.codingQuestion = codingQuestion;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
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
