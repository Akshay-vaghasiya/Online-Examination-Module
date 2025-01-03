package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/* The ExamResult class is an entity representing the result of a specific exam for a student.
   It links an exam and a student while storing details like the marks obtained and the pass status.

 * Purpose:
   * This class is used to store and manage the results of exams for individual students, including their scores
     and whether they have passed or failed.

 * Fields:
  - id: A unique identifier for each exam result entry, generated automatically. It is the primary key for the entity.
  - exam: A reference to the Exam entity, establishing a many-to-one relationship, linking the result to a specific exam.
  - student: A reference to the User entity representing the student, ensuring the result is associated with a specific user.
  - marksObtained: Stores the marks the student scored in the exam.
  - isPassed: A boolean flag indicating whether the student passed the exam based on the passing criteria.

 * Relationships:
  - @ManyToOne (exam): Establishes a many-to-one relationship with the Exam entity, linking each result to an exam.
  - @ManyToOne (student): Establishes a many-to-one relationship with the User entity, associating each result with a specific student. */
@Entity
@Table(name = "exam_results")
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    @JsonBackReference
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User student;

    @Column(nullable = false)
    private int marksObtained;

    @Column(nullable = false)
    private boolean isPassed;

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

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }
}
