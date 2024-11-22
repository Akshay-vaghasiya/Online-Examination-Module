package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/* The McqOption class represents an entity for storing individual answer options for a multiple-choice question (MCQ).
   Each option is linked to an MCQ question and contains information about the option's text and whether it is correct.

 * Purpose:
  * This class serves as a blueprint for creating the "mcq_options" table in the database, which stores
    the answer options for each MCQ question. It has a many-to-one relationship with the McqQuestion entity.

 * Fields:
  - id: A unique identifier for each option, auto-generated as the primary key.
  - mcqQuestion: Represents the associated MCQ question. This establishes a many-to-one relationship
    with the McqQuestion entity.
  - optionText: Stores the text of the option. This field is mandatory and can store up to 1,000 characters.
  - isCorrect: Indicates whether this option is the correct answer for the associated question. */
@Entity
@Table(name = "mcq_options")
public class McqOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference
    private McqQuestion mcqQuestion;

    @Column(name = "option_text", nullable = false, length = 1000)
    private String optionText;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public McqQuestion getMcqQuestion() {
        return mcqQuestion;
    }

    public void setMcqQuestion(McqQuestion mcqQuestion) {
        this.mcqQuestion = mcqQuestion;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean getisCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
