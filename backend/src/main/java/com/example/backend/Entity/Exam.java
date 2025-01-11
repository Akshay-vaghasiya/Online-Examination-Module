package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/* The Exam class is an entity representing an online exam in the system.
   It contains details about the exam, such as its name, duration, status, associated universities,
   and other metadata like difficulty level, total marks, and passing criteria.

 * Purpose:
   * This class is used to manage and store information about online exams,
     including their configuration and association with other entities like universities and questions.

 * Fields:
  - id: A unique identifier for each exam entry, generated automatically. It is the primary key for the entity.
  - examName: Stores the name of the exam, ensuring easy identification of the exam.
  - status: Indicates the current state of the exam using an enum (e.g., SCHEDULED, STARTED, COMPLETED).
  - duration: Specifies the total time allocated for the exam.
  - enable: A boolean flag indicating whether the exam is enabled or disabled.
  - universities: A set of universities associated with the exam through a many-to-many relationship.
  - passingMarks: The minimum marks required to pass the exam.
  - totalMarks: The total marks for the exam.
  - difficultyLevel: Represents the difficulty level of the exam (e.g., EASY, MEDIUM, HARD).
  - examQuestions: A one-to-many relationship with ExamQuestion, linking the exam to its questions.
  - studentExams: A one-to-many relationship with StudentExam, linking the exam to students' exam attempts.
  - scheduleDate: Stores the date of exam it will be conducted.

 * Relationships:
  - @ManyToMany (universities): Establishes a many-to-many relationship with the University entity,
    allowing exams to be associated with multiple universities.
  - @OneToMany (examQuestions): Establishes a one-to-many relationship with ExamQuestion,
    linking each exam to multiple questions.
  - @OneToMany (studentExams): Establishes a one-to-many relationship with StudentExam,
    linking each exam to students' participation and results. */
@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String examName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExamStatus status;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private boolean enable;

    @ManyToMany
    @JoinTable(
            name = "university_exams",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "university_id")
    )
    @JsonManagedReference
    private Set<University> universities = new HashSet<>();

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private int semester;

    @Column(nullable = false)
    private int passingMarks;

    @Column(nullable = false)
    private int totalMarks;

    @Column(nullable = false)
    private String difficultyLevel;

    @Column(nullable = false)
    private LocalDate scheduleDate;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ExamQuestion> examQuestions = new HashSet<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<StudentExam> studentExams = new HashSet<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ExamResult> examResults;

    public enum ExamStatus {
        SCHEDULED,
        STARTED,
        PAUSED,
        RESUMED,
        COMPLETED,
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Set<University> getUniversities() {
        return universities;
    }

    public void setUniversities(Set<University> universities) {
        this.universities = universities;
    }

    public int getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(int passingMarks) {
        this.passingMarks = passingMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Set<ExamQuestion> getExamQuestions() {
        return examQuestions;
    }

    public void setExamQuestions(Set<ExamQuestion> examQuestions) {
        this.examQuestions = examQuestions;
    }

    public Set<StudentExam> getStudentExams() {
        return studentExams;
    }

    public void setStudentExams(Set<StudentExam> studentExams) {
        this.studentExams = studentExams;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Set<ExamResult> getExamResults() {
        return examResults;
    }

    public void setExamResults(Set<ExamResult> examResults) {
        this.examResults = examResults;
    }
}