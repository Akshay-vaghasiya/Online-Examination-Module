package com.example.backend.Repository;

import com.example.backend.Entity.CodingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/* Repository interface for accessing and managing CodingQuestion entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional methods specific to
   CodingQuestion. It allows seamless interaction with the underlying database, enabling operations
   such as saving, finding, updating, and deleting coding questions.

 * Methods:
   - findByCategoryAndDifficultyLevel(String category, String difficultyLevel): Retrieves a list of CodingQuestion
     entities based on the specified category and difficulty level.

 * By extending JpaRepository, this interface also inherits various generic methods for data access
   and manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, Long> {

    List<CodingQuestion> findByCategoryAndDifficultyLevel(String category, String difficultyLevel);
}
