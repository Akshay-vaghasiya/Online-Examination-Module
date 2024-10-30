package com.example.backend.Repository;

import com.example.backend.Entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* Repository interface for accessing and managing PasswordResetToken entities in the database.

 * This interface extends JpaRepository to provide CRUD operations and additional methods specific to
   PasswordResetToken. It includes a method for finding a PasswordResetToken by its token string, which
   is essential for validating password reset requests.

 * Methods:
   - findByToken(String token): Retrieves a PasswordResetToken based on the unique token string.

 * By extending JpaRepository, this interface also inherits various generic methods for data access
    and manipulation, such as save, findById, findAll, delete, etc. */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}

