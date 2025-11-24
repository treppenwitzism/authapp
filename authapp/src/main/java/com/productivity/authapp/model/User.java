package com.productivity.authapp.model;

public class User {
    private String name;
    private String studentId;
    private String email;
    private String password;
    private String yearLevel;
    private String course;
    private String section;

    // Constructors
    public User() {}

    public User(String name, String studentId, String email, String password,
                String yearLevel, String course, String section) {
        this.name = name;
        this.studentId = studentId;
        this.email = email;
        this.password = password;
        this.yearLevel = yearLevel;
        this.course = course;
        this.section = section;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}