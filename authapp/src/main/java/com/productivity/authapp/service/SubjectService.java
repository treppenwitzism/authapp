package com.productivity.authapp.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.productivity.authapp.model.Subject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class SubjectService {

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    // Create a new subject
    public String createSubject(Subject subject) {
        try {
            Firestore db = getFirestore();

            String subjectId = UUID.randomUUID().toString();
            subject.setSubjectId(subjectId);

            Map<String, Object> subjectData = new HashMap<>();
            subjectData.put("subjectId", subject.getSubjectId());
            subjectData.put("userEmail", subject.getUserEmail());
            subjectData.put("name", subject.getName());
            subjectData.put("teacher", subject.getTeacher());
            subjectData.put("schedule", subject.getSchedule());
            subjectData.put("room", subject.getRoom());

            db.collection("users")
                    .document(subject.getUserEmail())
                    .collection("subjects")
                    .document(subjectId)
                    .set(subjectData)
                    .get();

            return "Subject created successfully";

        } catch (InterruptedException | ExecutionException e) {
            return "Error creating subject: " + e.getMessage();
        }
    }

    // Get all subjects for a user
    public List<Subject> getAllSubjects(String userEmail) {
        List<Subject> subjects = new ArrayList<>();

        try {
            Firestore db = getFirestore();

            List<QueryDocumentSnapshot> documents = db.collection("users")
                    .document(userEmail)
                    .collection("subjects")
                    .get()
                    .get()
                    .getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                Subject subject = new Subject();
                subject.setSubjectId(document.getString("subjectId"));
                subject.setUserEmail(document.getString("userEmail"));
                subject.setName(document.getString("name"));
                subject.setTeacher(document.getString("teacher"));
                subject.setSchedule(document.getString("schedule"));
                subject.setRoom(document.getString("room"));

                subjects.add(subject);
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching subjects: " + e.getMessage());
        }

        return subjects;
    }

    // Update a subject
    public String updateSubject(Subject subject) {
        try {
            Firestore db = getFirestore();

            Map<String, Object> subjectData = new HashMap<>();
            subjectData.put("name", subject.getName());
            subjectData.put("teacher", subject.getTeacher());
            subjectData.put("schedule", subject.getSchedule());
            subjectData.put("room", subject.getRoom());

            db.collection("users")
                    .document(subject.getUserEmail())
                    .collection("subjects")
                    .document(subject.getSubjectId())
                    .update(subjectData)
                    .get();

            return "Subject updated successfully";

        } catch (InterruptedException | ExecutionException e) {
            return "Error updating subject: " + e.getMessage();
        }
    }

    // Delete a subject
    public String deleteSubject(String userEmail, String subjectId) {
        try {
            Firestore db = getFirestore();

            db.collection("users")
                    .document(userEmail)
                    .collection("subjects")
                    .document(subjectId)
                    .delete()
                    .get();

            return "Subject deleted successfully";

        } catch (InterruptedException | ExecutionException e) {
            return "Error deleting subject: " + e.getMessage();
        }
    }
}