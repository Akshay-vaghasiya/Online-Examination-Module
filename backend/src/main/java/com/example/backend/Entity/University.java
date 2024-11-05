package com.example.backend.Entity;

import jakarta.persistence.*;

/* The University class represents an entity for a university in the database. It contains information about
   the university's name, address, contact details, and website. This class allows us to store and manage the
   details of different universities within the application.

 * Purpose:
  * The class serves as a blueprint for creating a "university" table in the database, which stores
    key details about each university, such as name, address, contact email, and website URL.

 * Fields:
  - universityId: A unique identifier for each university entry, auto-generated as the primary key.
  - universityName: Stores the name of the university. This field is unique and cannot be null.
  - address: Stores the address of the university.
  - contactEmail: Stores the contact email address for the university.
  - contactPhone: Stores the contact phone number for the university.
  - websiteUrl: Stores the URL of the university's website. */
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
