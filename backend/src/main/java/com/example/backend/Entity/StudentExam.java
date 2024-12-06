package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/* The StudentExam class is an entity representing an instance of a student attempting a specific exam.
   It tracks the exam, the student, and various metadata such as start time, end time, completion status,
   and the answers provided by the student.

 * Purpose:
   * This class is used to manage the participation of a student in a specific exam. It stores the details of
     the exam session, including when it started, when it ended (if completed), and whether the exam was completed.
     Additionally, it links to the answers submitted by the student during the exam.

 * Fields:
  - id: A unique identifier for each student exam record, generated automatically. It is the primary key for the entity.
  - exam: A reference to the Exam entity, linking the student exam record to the specific exam being attempted.
  - student: A reference to the User entity representing the student attempting the exam.
  - startTime: The timestamp when the student started the exam.
  - endTime: The timestamp when the student completed the exam, if applicable. Can be null if the exam is not yet completed.
  - isCompleted: A boolean flag indicating whether the student has completed the exam.
  - answers: A collection of StudentAnswer entities, representing the answers submitted by the student for the exam.

 * Relationships:
  - @ManyToOne (exam): Establishes a many-to-one relationship with the Exam entity, linking each student exam record to an exam.
  - @ManyToOne (student): Establishes a many-to-one relationship with the User entity, associating the record with a specific student.
  - @OneToMany (answers): Establishes a one-to-many relationship with the StudentAnswer entity,
    linking each student exam to the answers submitted during the exam. */
@Entity
@Table(name = "student_exam")
public class StudentExam {
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
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column(nullable = false)
    private boolean isCompleted;

    @OneToMany(mappedBy = "studentExam", cascade = CascadeType.ALL)
    private Set<StudentAnswer> answers = new HashSet<>();

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Set<StudentAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<StudentAnswer> answers) {
        this.answers = answers;
    }
}
