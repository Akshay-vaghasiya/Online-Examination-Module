package com.example.backend.Repository;

import com.example.backend.Entity.McqOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* Repository interface for accessing and managing McqOption entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional query capabilities for the
   McqOption entity. It allows seamless interaction with the underlying database, enabling operations such
   as saving, finding, updating, and deleting MCQ option.

 * By extending JpaRepository, this interface also inherits various generic methods for data access and
   manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface McqOptionRepository extends JpaRepository<McqOption, Long> {
}