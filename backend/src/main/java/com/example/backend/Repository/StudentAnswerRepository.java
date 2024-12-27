package com.example.backend.Repository;

import com.example.backend.Entity.StudentAnswer;
import com.example.backend.Entity.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/* Repository interface for accessing and managing StudentAnswer entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional query capabilities for the
   StudentAnswer entity. It allows seamless interaction with the underlying database, enabling operations such
   as saving, finding, updating, and deleting student answers to exam questions.

 * Methods:
   - findByStudentExamAndMcqQuestionId(StudentExam studentExam, Long mcqQuestionId): Retrieves a StudentAnswer entity based on
     the specified studentExam and mcqQuestionId.
   - findByStudentExamAndCodingQuestionId(StudentExam studentExam, Long codingQuestionId): Retrieves a StudentAnswer entity based on
     the specified studentExam and codingQuestionId.

 * By extending JpaRepository, this interface also inherits various generic methods for data access and
   manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    Optional<StudentAnswer> findByStudentExamAndMcqQuestionId(StudentExam studentExam, Long mcqQuestionId);
    Optional<StudentAnswer> findByStudentExamAndCodingQuestionId(StudentExam studentExam, Long codingQuestionId);
}
