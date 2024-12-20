package com.example.backend.Repository;

import com.example.backend.Entity.ExamQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/* Repository interface for accessing and managing ExamQuestion entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional query capabilities for the
   ExamQuestion entity. It allows seamless interaction with the underlying database, enabling operations such
   as saving, finding, updating, and deleting exam questions.

 * Methods:
   - findMcqQuestionsByExamId(Long examId, Pageable pageable): Retrieves a List of ExamQuestion entities contains mcq questions based on
     the specified examId and pageable and pageable have page number and size of page.
   - findCodingQuestionsByExamId(Long examId, Pageable pageable): Retrieves a List of ExamQuestion entities contains coding questions based on
     the specified examId and pageable and pageable have page number and size of page.

 * By extending JpaRepository, this interface also inherits various generic methods for data access and
   manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {

    @Query("SELECT eq FROM ExamQuestion eq WHERE eq.exam.id = :examId AND eq.mcqQuestion IS NOT NULL")
    Page<ExamQuestion> findMcqQuestionsByExamId(@Param("examId") Long examId, Pageable pageable);

    @Query("SELECT eq FROM ExamQuestion eq WHERE eq.exam.id = :examId AND eq.codingQuestion IS NOT NULL")
    Page<ExamQuestion> findCodingQuestionsByExamId(@Param("examId") Long examId, Pageable pageable);

}

