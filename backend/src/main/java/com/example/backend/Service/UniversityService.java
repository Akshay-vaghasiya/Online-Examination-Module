package com.example.backend.Service;

import com.example.backend.Entity.University;
import com.example.backend.Repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {

    // Automatically injects an instance of UniversityRepository
    @Autowired
    private UniversityRepository universityRepository;

    /* Saves a new university or updates an existing university in the database.

       param university - The University object to save.
       return - The saved University object. */
    public University saveUniversity(University university) {
        return universityRepository.save(university);
    }

    /* Retrieves a list of all universities from the database.

       return - A list of all universities. */
    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    /* Finds a university by its unique ID.

       param universityId - The ID of the university to find.
       return - An Optional containing the university if found, otherwise empty. */
    public Optional<University> getUniversityById(Long universityId) {
        return universityRepository.findById(universityId);
    }

    /* Finds a university by its name.

       param universityName - The name of the university to find.
       return - The University object if found, otherwise null. */
    public University getUniversityByName(String universityName) {
        return universityRepository.findByUniversityName(universityName);
    }

    /* Updates an existing university's details.

       param universityId - The ID of the university to update.
       param updatedUniversity - The University object containing updated details.
       return - The updated University object. */
    public University updateUniversity(Long universityId, University updatedUniversity) {
        University university = getUniversityById(universityId)
                .orElseThrow(() -> new RuntimeException("University not found with ID: " + universityId));

        university.setUniversityName(updatedUniversity.getUniversityName());
        university.setAddress(updatedUniversity.getAddress());
        university.setContactEmail(updatedUniversity.getContactEmail());
        university.setContactPhone(updatedUniversity.getContactPhone());
        university.setWebsiteUrl(updatedUniversity.getWebsiteUrl());

        return universityRepository.save(university);
    }

    /* Deletes a university by its unique ID.

       param universityId - The ID of the university to delete. */
    public void deleteUniversity(Long universityId) {
        universityRepository.deleteById(universityId);
    }
}

