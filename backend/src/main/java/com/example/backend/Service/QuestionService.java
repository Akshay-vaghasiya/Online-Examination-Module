package com.example.backend.Service;

import com.example.backend.Entity.CodingQuestion;
import com.example.backend.Entity.CodingTestCase;
import com.example.backend.Entity.McqOption;
import com.example.backend.Entity.McqQuestion;
import com.example.backend.Repository.CodingQuestionRepository;
import com.example.backend.Repository.McqOptionRepository;
import com.example.backend.Repository.McqQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {

    // Injects an instance of McqQuestionRepository for database operations
    @Autowired
    private McqQuestionRepository mcqQuestionRepository;

    // Injects an instance of CodingQuestionRepository for database operations
    @Autowired
    private CodingQuestionRepository codingQuestionRepository;

    // Injects an instance of McqOptionRepository for database operations
    @Autowired
    private McqOptionRepository mcqOptionRepository;

    /* Adds a new MCQ question to the database after validating the number of options.
       The number of options must be between 2 and 4.

       @param question - The MCQ question to add.
       @return The saved MCQ question object.
       @throws Exception - If the number of options is less than 2 or greater than 4. */
    public McqQuestion addMcqQuestion(McqQuestion question) throws Exception {

        if(question.getOptions().size() >= 2 && question.getOptions().size() <= 4) {

            for (McqOption option : question.getOptions()) {
                option.setMcqQuestion(question);
            }

            return mcqQuestionRepository.save(question);
        } else {
            throw new Exception("The number of options must be between 2 and 4.");
        }
    }

    /* Adds a new coding question along with its associated test cases to the database. Links each test
       case with the coding question before saving.

       @param question - The coding question to add.
       @return The saved CodingQuestion object. */
    public CodingQuestion addCodingQuestion(CodingQuestion question) {

        for (CodingTestCase testCase : question.getTestCases()) {
            testCase.setCodingQuestion(question);
        }

        return codingQuestionRepository.save(question);
    }

    /* Retrieves all MCQ questions from the database.

       @return A list of all MCQ questions stored in the database. */
    public List<McqQuestion> getMcqQuestion() {
        return mcqQuestionRepository.findAll();
    }

    /* Retrieves all coding questions from the database.

       @return A list of all coding questions stored in the database. */
    public List<CodingQuestion> getCodingQuestion() {
        return codingQuestionRepository.findAll();
    }

    /* Updates an existing MCQ question with new data, including options, question text,
       category, and difficulty level. Validates that the updated question has between 2 and 4 options.

       @param id - The ID of the MCQ question to update.
       @param updatedQuestion - The updated MCQ question object.
       @return The updated MCQ question object.
       @throws Exception - If the MCQ question is not found or the number of options is invalid. */
    public McqQuestion updateMcqQuestion(long id, McqQuestion updatedQuestion) throws Exception {

        McqQuestion mcqQuestion = mcqQuestionRepository.getReferenceById(id);

        if (updatedQuestion.getOptions().size() >= 2 && updatedQuestion.getOptions().size() <= 4 && mcqQuestion != null) {
            Set<McqOption> options = new HashSet<>();
            for (McqOption option : updatedQuestion.getOptions()) {
                option.setMcqQuestion(updatedQuestion);
                mcqOptionRepository.save(option);
                options.add(option);
            }
            mcqQuestion.setQuestionText(updatedQuestion.getQuestionText());
            mcqQuestion.setCategory(updatedQuestion.getCategory());
            mcqQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
            mcqQuestion.setOptions(options);

            return mcqQuestionRepository.save(mcqQuestion);
        } else {
            throw new Exception("MCQ question not found or invalid options.");
        }

    }

    /* Updates an existing coding question with new data, including its test cases, title,
       and other attributes. Links test cases with the updated question.

       @param id - The ID of the coding question to update.
       @param updatedQuestion - The updated coding question object.
       @return The updated CodingQuestion object. */
    public CodingQuestion updateCodingQuestion(long id, CodingQuestion updatedQuestion) {

        updatedQuestion.setId(id);

        for (CodingTestCase testCase : updatedQuestion.getTestCases()) {
            testCase.setCodingQuestion(updatedQuestion);
        }

        return codingQuestionRepository.save(updatedQuestion);

    }

    /* Deletes an existing MCQ question from the database by its ID.

       @param id - The ID of the MCQ question to delete. */
    public void deleteMcqQuestion(long id) {
        mcqQuestionRepository.deleteById(id);
    }

    /* Deletes an existing coding question from the database by its ID.

       @param id - The ID of the coding question to delete. */
    public void deleteCodingQuestion(long id) {
        codingQuestionRepository.deleteById(id);
    }
}