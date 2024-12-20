package com.example.backend.Dto;

/* The CodingTestCaseDTO class is used to represent a test case for a coding question.
   This class provides the input and expected output required to validate a student's solution.

 * Fields:
   - id: Contains the unique identifier for the test case.
   - inputData: Contains the input data of test case.
   - expectedOutput: Contains the expected output for the given input data.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of test case details. */
public class CodingTestCaseDTO {

    private Long id;
    private String inputData;
    private String expectedOutput;

    public CodingTestCaseDTO(Long id, String inputData, String expectedOutput) {
        this.id = id;
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
}
