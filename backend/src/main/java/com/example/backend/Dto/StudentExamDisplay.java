package com.example.backend.Dto;

import com.example.backend.Entity.Exam;

/* The StudentExamDisplay class is used to returned data to clients when displaying exams available to students.
   This class contains five main fields:
   * id, examName, status, duration, and enable, which provide details about an exam.

 * Fields:
   - id: Represents the unique identifier for the exam.
   - examName: Contains the name of the exam.
   - status: Contains the current status of the exam (e.g., STARTED, PAUSED, COMPLETED) using the enumerated type `Exam.ExamStatus`.
   - duration: Holds the duration of the exam in a string format in minutes.
   - enable: Contains boolean value and it's show that this exam is show to the user or not.

 * Methods:
   - Getter and Setter methods are provided for each field to allow controlled access and modification of exam details. */
public class StudentExamDisplay {

    private long id;
    private String examName;
    private Exam.ExamStatus status;
    private String duration;
    private boolean enable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Exam.ExamStatus getStatus() {
        return status;
    }

    public void setStatus(Exam.ExamStatus status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
