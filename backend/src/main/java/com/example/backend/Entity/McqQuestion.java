package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/* The McqQuestion class represents an entity for storing multiple-choice questions (MCQs) in the database.
   It contains information about the question's text, difficulty level, category, and associated options.

 * Purpose:
  * This class serves as a blueprint for creating the "mcq_questions" table in the database, which stores
    MCQ-related information. It uses a one-to-many relationship with the McqOption class to manage the
    answer options for each question.

 * Fields:
  - id: A unique identifier for each MCQ question, auto-generated as the primary key.
  - questionText: Stores the text of the MCQ question. This field is mandatory and can store long text.
  - difficultyLevel: Stores the difficulty level of the question (e.g., "Easy", "Medium", "Hard"). This is mandatory.
  - category: Stores the category of the question (e.g., "Programming", "Technical", "Aptitude"). This is mandatory.
  - options: Represents a set of possible answer options associated with the question. This field establishes
    a one-to-many relationship with the McqOption entity. */
@Entity
@Table(name = "mcq_questions")
public class McqQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false, length = 100000)
    private String questionText;

    @Column(name = "difficulty_level", nullable = false)
    private String difficultyLevel;

    @Column(name = "category", nullable = false)
    private String category;

    @OneToMany(mappedBy = "mcqQuestion", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<McqOption> options = new HashSet<>();

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

    public Set<McqOption> getOptions() {
        return options;
    }

    public void setOptions(Set<McqOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "McqQuestion{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", category='" + category + '\'' +
                ", options=" + options +
                '}';
    }
}