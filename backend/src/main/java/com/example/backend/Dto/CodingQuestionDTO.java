package com.example.backend.Dto;

import java.util.HashSet;
import java.util.Set;

/* The CodingQuestionDTO class is used to represent a coding question within an exam.
   This class provides details about the coding problem, including its description, category,
   function signature, and associated test cases.

 * Fields:
   - id: Contains the unique identifier for the coding question.
   - questionText: Contains the text of the coding question.
   - category: Contains the category to which the coding question belongs.
   - functionSignature: Contains function signature that student must implement as part of the solution.
   - testCases: Contains a set of test cases, encapsulated in the `CodingTestCaseDTO` class.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of the coding question details. */
public class CodingQuestionDTO {

    private Long id;
    private String questionText;
    private String category;
    private String functionSignature;
    private Set<CodingTestCaseDTO> testCases = new HashSet<>();

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

    public String getFunctionSignature() {
        return functionSignature;
    }

    public void setFunctionSignature(String functionSignature) {
        this.functionSignature = functionSignature;
    }

    public Set<CodingTestCaseDTO> getTestCases() {
        return testCases;
    }

    public void setTestCases(Set<CodingTestCaseDTO> testCases) {
        this.testCases = testCases;
    }
}
