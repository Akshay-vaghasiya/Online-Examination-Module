package com.example.backend.Dto;

/* The McqOptionDTO class is used to represent a single option for a MCQ.
   This class provides the text and identifier for each option.

 * Fields:
   - id: Contains the unique identifier for the MCQ option.
   - optionText: Contains the text of the option.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of the option details. */
public class McqOptionDTO {
    private Long id;
    private String optionText;

    public McqOptionDTO(Long id, String optionText) {
        this.id = id;
        this.optionText = optionText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
}
