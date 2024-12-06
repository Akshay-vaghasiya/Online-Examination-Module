package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/* The University class represents an entity for a university in the database. It contains information about
   the university's name, address, contact details, website, and associated exams. This class allows us to
   store and manage the details of different universities and their relationship with exams within the application.

 * Purpose:
   * The class serves as a blueprint for creating a "university" table in the database, which stores key details
     about each university, such as name, address, contact email, and website URL.
   * Additionally, it establishes a many-to-many relationship with the Exam entity, indicating that a university
     can have multiple associated exams, and an exam can be conducted by multiple universities.

 * Fields:
  - universityId: A unique identifier for each university entry, auto-generated as the primary key.
  - universityName: Stores the name of the university. This field is unique and cannot be null.
  - address: Stores the address of the university.
  - contactEmail: Stores the contact email address for the university.
  - contactPhone: Stores the contact phone number for the university.
  - websiteUrl: Stores the URL of the university's website.
  - exams: A set of exams associated with the university, representing the many-to-many relationship
           between universities and exams.

 * Relationships:
  - @ManyToMany (exams): Establishes a many-to-many relationship with the Exam entity. The exams field contains
    all the exams conducted by the university. This relationship is bidirectional, and the University side is the
    inverse (non-owning) side, mapped by the "universities" field in the Exam class. */
@Entity
@Table(name = "university")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long universityId;

    @Column(nullable = false, unique = true)
    private String universityName;

    private String address;
    private String contactEmail;
    private String contactPhone;
    private String websiteUrl;

    @ManyToMany(mappedBy = "universities")
    @JsonBackReference
    private Set<Exam> exams = new HashSet<>();

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }

    @Override
    public String toString() {
        return "University{" +
                "universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                ", address='" + address + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                '}';
    }
}
