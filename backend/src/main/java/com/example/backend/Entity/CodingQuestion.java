package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/* The CodingQuestion class represents an entity for storing coding-related questions in the database.
   It contains essential details about the question, including its text, title, difficulty level, category,
   function signature, and associated test cases. This class is designed to store programming questions
   that require the user to write code to solve a problem.

 * Purpose:
  * This class defines the "coding_questions" table in the database and serves as the blueprint for storing
    coding-related questions. Each question is associated with multiple test cases that validate the solution
    provided by the user. It is an essential component of the system that handles coding challenges.

 * Fields:
  - id: A unique identifier for each coding question, automatically generated as the primary key.
  - questionText: Stores the detailed description of the coding question, which is mandatory and can accommodate long text.
  - title: Stores the title of the coding question, which serves as a brief description or label for the problem.
  - difficultyLevel: Stores the difficulty level of the question (e.g., "Easy", "Medium", "Hard"). This is a mandatory field.
  - category: Represents the category of the coding question (e.g., "Algorithms", "Data Structures", "Mathematics"). This is mandatory.
  - functionSignature: Stores the function signature or method header that the user must implement. This is mandatory.
  - testCases: Represents a collection of test cases associated with the coding question. This field establishes
    a one-to-many relationship with the CodingTestCase entity. Each coding question can have multiple test cases,
    and each test case has input data and expected output that are used to validate user submissions. */
@Entity
@Table(name = "coding_questions")
public class CodingQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false, length = 900000)
    private String questionText;

    @Column(name = "question_title", nullable = false)
    private String title;

    @Column(name = "difficulty_level", nullable = false)
    private String difficultyLevel;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "function_signature")
    private String functionSignature;

    @OneToMany(mappedBy = "codingQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<CodingTestCase> testCases = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFunctionSignature() {
        return functionSignature;
    }

    public void setFunctionSignature(String functionSignature) {
        this.functionSignature = functionSignature;
    }

    public Set<CodingTestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(Set<CodingTestCase> testCases) {
        this.testCases = testCases;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}