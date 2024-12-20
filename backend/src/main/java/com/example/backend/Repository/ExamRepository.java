package com.example.backend.Repository;

import com.example.backend.Entity.Exam;
import com.example.backend.Entity.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/* Repository interface for accessing and managing Exam entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional query capabilities for the
   Exam entity. It allows seamless interaction with the underlying database, enabling operations such
   as saving, finding, updating, and deleting exams.

 * Methods:
   - findExamsByUniversityId(Long universityId): Retrieves a List of Exam entities based on the specified universityId.
   - existsByStudentEmail(String email): Check whether student present in studentExam list or not.

 * By extending JpaRepository, this interface also inherits various generic methods for data access and
   manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query("SELECT e FROM Exam e JOIN e.universities u WHERE u.universityId = :universityId")
    List<Exam> findExamsByUniversityId(@Param("universityId") Long universityId);

    @Query("SELECT CASE WHEN COUNT(se) > 0 THEN true ELSE false END FROM StudentExam se WHERE se.student.email = :email")
    boolean existsByStudentEmail(@Param("email") String email);
}