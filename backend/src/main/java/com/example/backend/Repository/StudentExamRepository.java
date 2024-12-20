package com.example.backend.Repository;

import com.example.backend.Entity.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/* Repository interface for accessing and managing StudentExam entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional query capabilities for the
   StudentExam entity. It allows seamless interaction with the underlying database, enabling operations such
   as saving, finding, updating, and deleting student exam records.

 * Methods:
   - findByStudentEmailAndExamId(String email, Long examId): Retrieves a StudentExam entity based on
     the specified examId and email level.

 * By extending JpaRepository, this interface also inherits various generic methods for data access and
   manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, Long> {

    @Query("SELECT se FROM StudentExam se WHERE se.student.email = :email AND se.exam.id = :examId")
    Optional<StudentExam> findByStudentEmailAndExamId(@Param("email") String email, @Param("examId") Long examId);
}
