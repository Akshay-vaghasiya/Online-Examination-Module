package com.example.backend.Repository;

import com.example.backend.Entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* Repository interface for accessing and managing University entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional methods specific to
   University. It includes a method for finding a User by universityName, which is particularly
   useful as universityName is a unique identifier for university in this application.

 * Methods:
   - findByUniversityName(String universityName): Retrieves a University based on the unique universityName string.

 * By extending JpaRepository, this interface also inherits various generic methods for data access
    and manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    University findByUniversityName(String universityName);
}

