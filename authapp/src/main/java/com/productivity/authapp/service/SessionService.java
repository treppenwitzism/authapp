package com.productivity.authapp.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.productivity.authapp.model.Session;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class SessionService {

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    // Create a new study session
    public String createSession(Session session) {
        try {
            Firestore db = getFirestore();

            // Generate unique session ID
            String sessionId = UUID.randomUUID().toString();
            session.setSessionId(sessionId);

            // Prepare session data
            Map<String, Object> sessionData = new HashMap<>();
            sessionData.put("sessionId", session.getSessionId());
            sessionData.put("userEmail", session.getUserEmail());
            sessionData.put("sessionName", session.getSessionName());
            sessionData.put("subject", session.getSubject());
            sessionData.put("duration", session.getDuration());
            sessionData.put("rating", session.getRating());
            sessionData.put("notes", session.getNotes() != null ? session.getNotes() : "");
            sessionData.put("createdAt", session.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            // Store in Firestore under users/{email}/sessions/{sessionId}
            db.collection("users")
                    .document(session.getUserEmail())
                    .collection("sessions")
                    .document(sessionId)
                    .set(sessionData)
                    .get();

            return "Session created successfully";

        } catch (InterruptedException | ExecutionException e) {
            return "Error creating session: " + e.getMessage();
        }
    }

    // Get all sessions for a user
    public List<Session> getAllSessions(String userEmail) {
        List<Session> sessions = new ArrayList<>();

        try {
            Firestore db = getFirestore();

            List<QueryDocumentSnapshot> documents = db.collection("users")
                    .document(userEmail)
                    .collection("sessions")
                    .get()
                    .get()
                    .getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                Session session = new Session();
                session.setSessionId(document.getString("sessionId"));
                session.setUserEmail(document.getString("userEmail"));
                session.setSessionName(document.getString("sessionName"));
                session.setSubject(document.getString("subject"));
                session.setDuration(document.getLong("duration").intValue());
                session.setRating(document.getLong("rating") != null ? document.getLong("rating").intValue() : 0);
                session.setNotes(document.getString("notes"));

                String createdAtStr = document.getString("createdAt");
                if (createdAtStr != null) {
                    session.setCreatedAt(LocalDateTime.parse(createdAtStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                }

                sessions.add(session);
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching sessions: " + e.getMessage());
        }

        return sessions;
    }

    // Update an existing session
    public String updateSession(Session session) {
        try {
            Firestore db = getFirestore();

            // Prepare updated session data
            Map<String, Object> sessionData = new HashMap<>();
            sessionData.put("sessionName", session.getSessionName());
            sessionData.put("subject", session.getSubject());
            sessionData.put("duration", session.getDuration());
            sessionData.put("rating", session.getRating());
            sessionData.put("notes", session.getNotes() != null ? session.getNotes() : "");

            db.collection("users")
                    .document(session.getUserEmail())
                    .collection("sessions")
                    .document(session.getSessionId())
                    .update(sessionData)
                    .get();

            return "Session updated successfully";

        } catch (InterruptedException | ExecutionException e) {
            return "Error updating session: " + e.getMessage();
        }
    }

    // Delete a session
    public String deleteSession(String userEmail, String sessionId) {
        try {
            Firestore db = getFirestore();

            db.collection("users")
                    .document(userEmail)
                    .collection("sessions")
                    .document(sessionId)
                    .delete()
                    .get();

            return "Session deleted successfully";

        } catch (InterruptedException | ExecutionException e) {
            return "Error deleting session: " + e.getMessage();
        }
    }
}