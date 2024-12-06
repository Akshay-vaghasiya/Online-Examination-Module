package com.example.backend.Entity;

import jakarta.persistence.*;

/* The StudentAnswer class is an entity representing an answer submitted by a student during an exam.
   It tracks the question (MCQ or coding), the answer provided, and metadata like whether the answer is correct
   and the programming language used for coding questions.

 * Purpose:
   * This class is used to store and manage answers submitted by students during exams. It links to the
     student exam session, the corresponding question (MCQ or coding), and the answer details,
     including correctness and language used (for coding).

 * Fields:
  - id: A unique identifier for each answer record, generated automatically. It is the primary key for the entity.
  - studentExam: A reference to the StudentExam entity, linking this answer to a specific student exam session.
  - mcqQuestion: A reference to the MCQ question answered by the student, if applicable.
  - codingQuestion: A reference to the coding question answered by the student, if applicable.
  - answer: A string representing the student's answer. For MCQs, it could be an option; for coding, the submitted solution.
  - language: The programming language used for coding answers, nullable for MCQs.
  - isCorrect: A boolean flag indicating whether the student's answer is correct, nullable for coding questions
               until evaluated.

 * Relationships:
  - @ManyToOne (studentExam): Establishes a many-to-one relationship with the StudentExam entity,
    associating the answer with a specific exam session.
  - @ManyToOne (mcqQuestion): Establishes a many-to-one relationship with the McqQuestion entity,
    linking this answer to an MCQ question, if applicable.
  - @ManyToOne (codingQuestion): Establishes a many-to-one relationship with the CodingQuestion entity,
    linking this answer to a coding question, if applicable. */
@Entity
@Table(name = "student_answers")
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_exam_id", nullable = false)
    private StudentExam studentExam;

    @ManyToOne
    @JoinColumn(name = "mcq_question_id", nullable = true)
    private McqQuestion mcqQuestion;

    @ManyToOne
    @JoinColumn(name = "coding_question_id", nullable = true)
    private CodingQuestion codingQuestion;

    @Column(nullable = false, length = Integer.MAX_VALUE)
    private String answer;

    private String language;

    @Column(nullable = true)
    private boolean isCorrect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentExam getStudentExam() {
        return studentExam;
    }

    public void setStudentExam(StudentExam studentExam) {
        this.studentExam = studentExam;
    }

    public McqQuestion getMcqQuestion() {
        return mcqQuestion;
    }

    public void setMcqQuestion(McqQuestion mcqQuestion) {
        this.mcqQuestion = mcqQuestion;
    }

    public CodingQuestion getCodingQuestion() {
        return codingQuestion;
    }

    public void setCodingQuestion(CodingQuestion codingQuestion) {
        this.codingQuestion = codingQuestion;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
