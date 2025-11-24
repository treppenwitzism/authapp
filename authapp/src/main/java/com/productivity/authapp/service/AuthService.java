package com.productivity.authapp.service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import com.productivity.authapp.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class AuthService {

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    // Sign up - creates new user
    public String signUp(User user) {
        try {
            Firestore db = getFirestore();

            // Check if user already exists
            DocumentSnapshot document = db.collection("users")
                    .document(user.getEmail())
                    .get()
                    .get();

            if (document.exists()) {
                return "User already exists";
            }

            // Create new user with all fields
            Map<String, Object> userData = new HashMap<>();
            userData.put("name", user.getName());
            userData.put("studentId", user.getStudentId());
            userData.put("email", user.getEmail());
            userData.put("password", user.getPassword()); // In production, hash this!
            userData.put("yearLevel", user.getYearLevel());
            userData.put("course", user.getCourse());
            userData.put("section", user.getSection());

            db.collection("users")
                    .document(user.getEmail())
                    .set(userData)
                    .get();

            return "Sign up successful";

        } catch (InterruptedException | ExecutionException e) {
            return "Error during sign up: " + e.getMessage();
        }
    }

    // Login - validates credentials
    public String login(User user) {
        try {
            Firestore db = getFirestore();

            DocumentSnapshot document = db.collection("users")
                    .document(user.getEmail())
                    .get()
                    .get();

            if (!document.exists()) {
                return "User not found";
            }

            String storedPassword = document.getString("password");

            if (storedPassword != null && storedPassword.equals(user.getPassword())) {
                return "Login successful";
            } else {
                return "Invalid password";
            }

        } catch (InterruptedException | ExecutionException e) {
            return "Error during login: " + e.getMessage();
        }
    }
}