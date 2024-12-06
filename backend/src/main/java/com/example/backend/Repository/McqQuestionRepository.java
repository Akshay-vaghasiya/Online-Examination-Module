package com.example.backend.Repository;

import com.example.backend.Entity.McqQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/* Repository interface for accessing and managing McqQuestion entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional methods specific to
   McqQuestion. It allows seamless interaction with the underlying database, enabling operations
   such as saving, finding, updating, and deleting MCQ questions.

 * Methods:
   - findByCategoryAndDifficultyLevel(String category, String difficultyLevel): Retrieves a list of McqQuestion
     entities based on the specified category and difficulty level.

 * By extending JpaRepository, this interface also inherits various generic methods for data access
   and manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface McqQuestionRepository extends JpaRepository<McqQuestion, Long> {

    List<McqQuestion> findByCategoryAndDifficultyLevel(String category, String difficultyLevel);
}
