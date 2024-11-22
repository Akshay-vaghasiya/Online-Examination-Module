package com.example.backend.Repository;

import com.example.backend.Entity.CodingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* Repository interface for accessing and managing CodingQuestion entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional methods specific to
   the CodingQuestion entity. It facilitates data retrieval and manipulation related to coding questions,
   such as saving, updating, and deleting questions from the database.

 * By extending JpaRepository, this interface inherits various generic methods for data access, including
   save, findById, findAll, delete, etc., and can be used to perform operations on the "coding_questions" table. */
@Repository
public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, Long> {
}

