package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/* Entity class representing a coding test case associated with a coding question.

 * This class is used to store the input data and expected output for a specific coding test case. Each test case
   is linked to a particular coding question, allowing the system to evaluate user-submitted code against a set of
   predefined test cases. It plays a key role in verifying whether a solution to a coding problem produces the correct
   results based on different inputs.

 * Purpose:
  * This class represents a test case for a coding question, stored in the "coding_test_cases" table in the
    database. It allows the system to store both the input data and the expected output, which are then used to
    validate the correctness of a user-submitted solution.

 * Fields:
  - id: A unique identifier for each coding test case, auto-generated as the primary key.
  - codingQuestion: The coding question associated with this test case. This field establishes a many-to-one
    relationship with the CodingQuestion entity, linking each test case to a specific question.
  - inputData: The input data provided to the user's code when executing the test case. This field stores the
    input as a string, allowing for complex data structures to be provided in text form.
  - expectedOutput: The expected output for the test case. This field stores the correct result that the user's
    code should produce when given the corresponding input data. */
 @Entity
@Table(name = "coding_test_cases")
public class CodingTestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference
    private CodingQuestion codingQuestion;

    @Column(name = "input_data", nullable = false, length = 100000)
    private String inputData;

    @Column(name = "expected_output", nullable = false, length = 100000)
    private String expectedOutput;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CodingQuestion getCodingQuestion() {
        return codingQuestion;
    }

    public void setCodingQuestion(CodingQuestion codingQuestion) {
        this.codingQuestion = codingQuestion;
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