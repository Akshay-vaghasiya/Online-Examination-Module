package com.example.backend.Dto;

/* The CodeRequest class is used to represent the details of a code run.
   This class provides details about the code, the programming language used, and any input provided.

 * Fields:
   - sourceCode: Contains the source code written by the student.
   - languageId: Represents the unique identifier of the programming language.
   - stdin: Contains standard input data that will be passed to api which run source code.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of the code request details. */
public class CodeRequest {
    private String sourceCode;
    private int languageId;
    private String stdin;

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getStdin() {
        return stdin;
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }
}
