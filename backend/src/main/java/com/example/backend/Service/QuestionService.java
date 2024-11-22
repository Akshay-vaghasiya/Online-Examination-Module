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

@Service
public class QuestionService {

    // Injects an instance of McqQuestionRepository for database operations
    @Autowired
    private McqQuestionRepository mcqQuestionRepository;

    // Injects an instance of CodingQuestionRepository for database operations
    @Autowired
    private CodingQuestionRepository codingQuestionRepository;
    
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
}