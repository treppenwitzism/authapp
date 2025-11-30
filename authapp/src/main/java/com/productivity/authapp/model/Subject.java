package com.productivity.authapp.model;

public class Subject {
    private String subjectId;
    private String userEmail;
    private String name;
    private String teacher;
    private String schedule;
    private String room;

    // Constructors
    public Subject() {}

    public Subject(String subjectId, String userEmail, String name,
                   String teacher, String schedule, String room) {
        this.subjectId = subjectId;
        this.userEmail = userEmail;
        this.name = name;
        this.teacher = teacher;
        this.schedule = schedule;
        this.room = room;
    }

    // Getters and Setters
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}