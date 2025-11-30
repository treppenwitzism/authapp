package com.productivity.authapp.controller;

import com.productivity.authapp.model.Session;
import com.productivity.authapp.model.Subject;
import com.productivity.authapp.service.SessionService;
import com.productivity.authapp.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SubjectService subjectService;

    // Serve the dashboard page
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    // Create a new session
    @PostMapping("/create")
    @ResponseBody
    public String createSession(@RequestBody Session session) {
        return sessionService.createSession(session);
    }

    // Get all sessions for a user
    @GetMapping("/list")
    @ResponseBody
    public List<Session> getAllSessions(@RequestParam String userEmail) {
        return sessionService.getAllSessions(userEmail);
    }

    // Update a session
    @PutMapping("/update")
    @ResponseBody
    public String updateSession(@RequestBody Session session) {
        return sessionService.updateSession(session);
    }

    // Delete a session
    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteSession(@RequestParam String userEmail, @RequestParam String sessionId) {
        return sessionService.deleteSession(userEmail, sessionId);
    }

    // Subject endpoints
    @PostMapping("/subject/create")
    @ResponseBody
    public String createSubject(@RequestBody Subject subject) {
        return subjectService.createSubject(subject);
    }

    @GetMapping("/subject/list")
    @ResponseBody
    public List<Subject> getAllSubjects(@RequestParam String userEmail) {
        return subjectService.getAllSubjects(userEmail);
    }

    @PutMapping("/subject/update")
    @ResponseBody
    public String updateSubject(@RequestBody Subject subject) {
        return subjectService.updateSubject(subject);
    }

    @DeleteMapping("/subject/delete")
    @ResponseBody
    public String deleteSubject(@RequestParam String userEmail, @RequestParam String subjectId) {
        return subjectService.deleteSubject(userEmail, subjectId);
    }
}