package com.example.backend.Service;

import com.example.backend.Dto.ExamCreateRequest;
import com.example.backend.Entity.*;
import com.example.backend.Repository.CodingQuestionRepository;
import com.example.backend.Repository.ExamRepository;
import com.example.backend.Repository.McqQuestionRepository;
import com.example.backend.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExamService {

    // Injects an instance of McqQuestionRepository for database operations
    @Autowired
    private McqQuestionRepository mcqQuestionRepository;

    // Injects an instance of CodingQuestionRepository for database operations
    @Autowired
    private CodingQuestionRepository codingQuestionRepository;

    // Injects an instance of ExamRepository for database operations
    @Autowired
    private ExamRepository examRepository;

    // Injects an instance of UniversityRepository for database operations
    @Autowired
    private UniversityRepository universityRepository;

    /* Creates a new exam based on the provided ExamCreateRequest. This method handles the creation of the exam,
       including setting up exam details, generating questions, and associating universities. The newly created
       exam is then saved to the repository.

       param examCreateRequest - Contains all the necessary data to create the exam, including exam name,
                                 question categories, difficulty levels, and associated universities.
       return - The saved Exam entity after creation.
       throws Exception - If any university is not found or if there are not enough questions available
                          for the given categories and difficulty levels. */
    public Exam createExam(ExamCreateRequest examCreateRequest) throws Exception {
        Exam exam = new Exam();

        Set<ExamQuestion> questionSet = createQuestions(examCreateRequest, exam);
        Set<University> universities = getUniversities(examCreateRequest.getUniversities());

        exam.setExamName(examCreateRequest.getExamName());
        exam.setExamQuestions(questionSet);
        exam.setDifficultyLevel(examCreateRequest.getDifficultyLevel());
        exam.setPassingMarks(examCreateRequest.getPassingMarks());
        exam.setTotalMarks(examCreateRequest.getTotalMarks());
        exam.setEnable(examCreateRequest.isEnable());
        exam.setDuration(examCreateRequest.getDuration());
        exam.setStatus(examCreateRequest.getStatus());
        exam.setUniversities(universities);

        return examRepository.save(exam);
    }

    /* Retrieves a set of universities based on the provided university names. This method searches for
       universities in the repository and adds them to the result set. If any university is not found,
       an exception is thrown.

       param universityNames - A set of university names to be fetched from the database.
       return - A set of University entities corresponding to the provided names.
       throws Exception - If a university name is not found in the database. */
    private Set<University> getUniversities(Set<String> universityNames) throws Exception {
        Set<University> universities = new HashSet<>();
        for (String universityName : universityNames) {
            University university = universityRepository.findByUniversityName(universityName);
            if (university == null) {
                throw new Exception("University not found: " + universityName);
            }
            universities.add(university);
        }
        return universities;
    }

    /* Creates a set of exam questions for the exam based on the provided ExamCreateRequest. This method fetches
       the MCQ and coding questions from their respective repositories, filters them based on the provided
       categories and difficulty levels, and creates ExamQuestion entities.

       param examCreateRequest - Contains all the data required to generate the questions (categories,
                                 difficulty levels, number of questions).
       param exam - The exam to which the generated questions will be added.
       return - A set of ExamQuestion entities to be associated with the exam.
       throws Exception - If there are not enough questions available for the given categories and difficulty levels. */
    private Set<ExamQuestion> createQuestions(ExamCreateRequest examCreateRequest, Exam exam) throws Exception {
        Set<ExamQuestion> examQuestions = new HashSet<>();
        for(Map<String, Object> questiontype : examCreateRequest.getMcqQuestions()) {
            examQuestions.addAll(getMcqExamQuestion((String)questiontype.get("category"),
                    (int)questiontype.get("noOfQuestion"),
                    examCreateRequest.getDifficultyLevel(),
                    exam));
        }

        for(Map<String, Object> questiontype : examCreateRequest.getCodingQuestions()) {
            examQuestions.addAll(getCodingExamQuesiont((String)questiontype.get("category"),
                    (int)questiontype.get("noOfQuestion"),
                    examCreateRequest.getDifficultyLevel(),
                    exam));
        }

        return examQuestions;
    }

    /* Generates a set of exam questions for coding questions based on the category, number of questions,
       difficulty level, and associated exam. This method fetches coding questions from the repository,
       filters them based on the provided criteria, and creates corresponding ExamQuestion entities.

       param category - The category of coding questions to be fetched.
       param noOfQuestion - The number of coding questions to be included in the exam.
       param difficultyLevel - The difficulty level of the questions (Easy, Medium, Hard).
       param exam - The exam to which the generated questions will be added.
       return - A set of ExamQuestion entities containing the selected coding questions.
       throws IllegalArgumentException - If there are not enough coding questions for the specified category and difficulty level. */
    private Set<ExamQuestion> getCodingExamQuesiont(String category, int noOfQuestion, String difficultyLevel, Exam exam) {
        List<CodingQuestion> filteredCodingQuestions = codingQuestionRepository.findByCategoryAndDifficultyLevel(category, difficultyLevel);

        if (filteredCodingQuestions.size() < noOfQuestion) {
            throw new IllegalArgumentException("Not enough Coding questions available for category: "
                    + category + " and difficulty level: " + difficultyLevel);
        }

        Collections.shuffle(filteredCodingQuestions);
        List<CodingQuestion> selectedQuestions = filteredCodingQuestions.subList(0, noOfQuestion);

        Set<ExamQuestion> examQuestions = new HashSet<>();
        for (CodingQuestion codingQuestion : selectedQuestions) {
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExam(exam);
            examQuestion.setCodingQuestion(codingQuestion);
            if(difficultyLevel.equalsIgnoreCase("Easy")) {
                examQuestion.setMarks(20);
            } else if (difficultyLevel.equalsIgnoreCase("Medium")) {
                examQuestion.setMarks(30);
            } else {
                examQuestion.setMarks(40);
            }
            examQuestions.add(examQuestion);
        }

        return examQuestions;
    }

    /* Generates a set of exam questions for MCQ questions based on the category, number of questions,
       difficulty level, and associated exam. This method fetches MCQ questions from the repository,
       filters them based on the provided criteria, and creates corresponding ExamQuestion entities.

       param category - The category of MCQ questions to be fetched.
       param noOfQuestion - The number of MCQ questions to be included in the exam.
       param difficultyLevel - The difficulty level of the questions (Easy, Medium, Hard).
       param exam - The exam to which the generated questions will be added.
       return - A set of ExamQuestion entities containing the selected MCQ questions.
       throws IllegalArgumentException - If there are not enough MCQ questions for the specified category and difficulty level. */
    private Set<ExamQuestion> getMcqExamQuestion(String category, int noOfQuestion, String difficultyLevel, Exam exam) {
        List<McqQuestion> filteredMcqQuestions = mcqQuestionRepository.findByCategoryAndDifficultyLevel(category, difficultyLevel);

        if (filteredMcqQuestions.size() < noOfQuestion) {
            throw new IllegalArgumentException("Not enough MCQ questions available for category: "
                    + category + " and difficulty level: " + difficultyLevel);
        }

        Collections.shuffle(filteredMcqQuestions);
        List<McqQuestion> selectedQuestions = filteredMcqQuestions.subList(0, noOfQuestion);

        Set<ExamQuestion> examQuestions = new HashSet<>();
        for (McqQuestion mcqQuestion : selectedQuestions) {
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExam(exam);
            examQuestion.setMcqQuestion(mcqQuestion);

            if(difficultyLevel.equalsIgnoreCase("Easy")) {
                examQuestion.setMarks(1);
            } else if (difficultyLevel.equalsIgnoreCase("Medium")) {
                examQuestion.setMarks(2);
            } else {
                examQuestion.setMarks(3);
            }

            examQuestions.add(examQuestion);
        }

        return examQuestions;
    }

    /* Updates an existing exam with the details from the provided ExamCreateRequest. This method modifies
       the exam's properties and updates the associated universities. The updated exam is then saved to the repository.

       param id - The ID of the exam to be updated.
       param examCreateRequest - Contains the new details for the exam, including exam name, status, and associated universities.
       return - The updated Exam entity after saving.
       throws Exception - If any university is not found for the exam. */
    public Exam updateExam(long id, ExamCreateRequest examCreateRequest) throws Exception {

        Exam exam = examRepository.getReferenceById(id);

        exam.setEnable(examCreateRequest.isEnable());
        exam.setStatus(examCreateRequest.getStatus());
        exam.setExamName(examCreateRequest.getExamName());
        exam.setDuration(examCreateRequest.getDuration());
        exam.setPassingMarks(examCreateRequest.getPassingMarks());
        exam.setDifficultyLevel(examCreateRequest.getDifficultyLevel());

        Set<University> universities = getUniversities(examCreateRequest.getUniversities());
        exam.setUniversities(universities);

        return examRepository.save(exam);
    }

    /* Deletes an exam based on its ID. This method removes the exam from the repository.

       param id - The ID of the exam to be deleted. */
    public void deleteExam(long id) {
        examRepository.deleteById(id);
    }

    /* Retrieves a list of all exams from the repository.

       return - A list of all Exam entities. */
    public List<Exam> getExams() {
        return examRepository.findAll();
    }
}