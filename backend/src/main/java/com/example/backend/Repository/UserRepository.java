package com.example.backend.Repository;

import com.example.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/* Repository interface for accessing and managing User entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional methods to
   retrieve User data. It includes a method for finding a User by email, which is particularly
   useful as email is a unique identifier for users in this application.

 * Methods:
 - findByEmail(String email): Retrieves an Optional<User> based on the unique email.

 * By extending JpaRepository, it also inherits various generic methods for data access and
   manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
