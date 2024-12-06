package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/* The ExamQuestion class is an entity representing the questions assigned to a specific exam.
   Each entry in this entity links an exam to either an MCQ or a coding question, along with its assigned marks.

 * Purpose:
   * This class is used to manage the association between exams and their questions,
     including both MCQ and coding questions, and their respective marks.

 * Fields:
  - id: A unique identifier for each exam question entry, generated automatically. It is the primary key for the entity.
  - exam: A reference to the Exam entity, establishing a many-to-one relationship, linking each question to an exam.
  - mcqQuestion: A reference to an MCQ question entity, nullable to allow either an MCQ or a coding question.
  - codingQuestion: A reference to a coding question entity, nullable to allow either a coding or an MCQ question.
  - marks: Specifies the marks assigned to this particular question in the exam.

 * Relationships:
  - @ManyToOne (exam): Establishes a many-to-one relationship with the Exam entity, linking each question to its exam.
  - @ManyToOne (mcqQuestion): Links the question to an MCQQuestion entity, allowing null values if the question is not an MCQ.
  - @ManyToOne (codingQuestion): Links the question to a CodingQuestion entity, allowing null values if the question is not a coding question. */
@Entity
@Table(name = "exam_questions")
public class ExamQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    @JsonBackReference
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "mcq_question_id", nullable = true)
    private McqQuestion mcqQuestion;

    @ManyToOne
    @JoinColumn(name = "coding_question_id", nullable = true)
    private CodingQuestion codingQuestion;

    @Column(nullable = false)
    private int marks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
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

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}