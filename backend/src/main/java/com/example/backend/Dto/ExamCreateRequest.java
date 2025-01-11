package com.example.backend.Dto;

import com.example.backend.Entity.Exam;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* The ExamCreateRequest class is a Data Transfer Object (DTO) used to encapsulate the data needed to
   create a new exam or update an existing exam. This class contains fields :
    * mcqQuestions, codingQuestions, status, enable, examName, duration, universities, passingMarks, totalMarks,
      difficultyLevel, scheduleDate, which are required for create an exam.

 * Fielda :
  - mcqQuestions - It will contain categories and noOfQuestions per category of mcq questions.
  - codingQuestions - It will contain categories and noOfQuestions per category of coding questions.
  - status - It show that exam status.
  - enable - It is boolean variable for start exam from admin side.
  - examName - Assign one name to every exam.
  - duration - It will contain exam duration in minutes.
  - universities - It will contain universities which is eligible for exam.
  - passingMarks - It will contain integer value as passing marks.
  - totalMarks - It will contain integer value as total marks of exam.
  - difficultyLevel - It will contain exam difficulty level.
  - scheduleDate - It will contain date of exam on which it will be conducted.

 * Methods:
  - Getter and Setter methods for each field are provided to allow controlled access and modification of the data fields. */
public class ExamCreateRequest {

    private Set<Map<String, Object>> mcqQuestions;

    private Set<Map<String, Object>> codingQuestions;

    private Exam.ExamStatus status;
    private boolean enable;
    private String examName;

    private String duration;
    private Set<String> universities = new HashSet<>();

    private LocalDate scheduleDate;
    private String branch;
    private int semester;

    private int passingMarks;
    private int totalMarks;
    private String difficultyLevel;

    public Set<Map<String, Object>> getMcqQuestions() {
        return mcqQuestions;
    }

    public void setMcqQuestions(Set<Map<String, Object>> mcqQuestions) {
        this.mcqQuestions = mcqQuestions;
    }

    public Set<Map<String, Object>> getCodingQuestions() {
        return codingQuestions;
    }

    public void setCodingQuestions(Set<Map<String, Object>> codingQuestions) {
        this.codingQuestions = codingQuestions;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Set<String> getUniversities() {
        return universities;
    }

    public void setUniversities(Set<String> universities) {
        this.universities = universities;
    }

    public int getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(int passingMarks) {
        this.passingMarks = passingMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Exam.ExamStatus getStatus() {
        return status;
    }

    public void setStatus(Exam.ExamStatus status) {
        this.status = status;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}