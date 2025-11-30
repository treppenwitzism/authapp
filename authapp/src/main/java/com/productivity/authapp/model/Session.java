package com.productivity.authapp.model;

import java.time.LocalDateTime;

public class Session {
    private String sessionId;
    private String userEmail;
    private String sessionName;
    private String subject;
    private int duration; // in minutes
    private int rating; // 1-5 scale
    private String notes;
    private LocalDateTime createdAt;

    // Constructors
    public Session() {
        this.createdAt = LocalDateTime.now();
    }

    public Session(String sessionId, String userEmail, String sessionName,
                   String subject, int duration, int rating, String notes) {
        this.sessionId = sessionId;
        this.userEmail = userEmail;
        this.sessionName = sessionName;
        this.subject = subject;
        this.duration = duration;
        this.rating = rating;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}