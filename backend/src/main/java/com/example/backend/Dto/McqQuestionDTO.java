package com.example.backend.Dto;

import java.util.HashSet;
import java.util.Set;

/* The McqQuestionDTO class is used to represent a MCQ within an exam.
   This class provides details about the MCQ, and including its text, category, and available options.

 * Fields:
   - id: Represents the unique identifier for the MCQ.
   - questionText: Contains the text of the MCQ.
   - category: Contains the category to which the MCQ belongs.
   - options: Contains a set of answers for the MCQ, encapsulated in the `McqOptionDTO` class.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of the MCQ details. */
public class McqQuestionDTO {
    private Long id;
    private String questionText;
    private String category;
    private Set<McqOptionDTO> options = new HashSet<>();

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<McqOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(Set<McqOptionDTO> options) {
        this.options = options;
    }
}
