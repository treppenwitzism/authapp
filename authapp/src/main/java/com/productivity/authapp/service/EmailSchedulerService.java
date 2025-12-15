package com.productivity.authapp.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.productivity.authapp.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmailSchedulerService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SessionService sessionService;

    private Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void sendWeeklyReports() {
        System.out.println("Starting weekly report generation...");

        try {
            Set<String> userEmails = getAllUserEmails();
            LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

            for (String email : userEmails) {
                List<Session> allSessions = sessionService.getAllSessions(email);

                List<Session> weeklySessions = allSessions.stream()
                        .filter(s -> s.getCreatedAt().isAfter(oneWeekAgo))
                        .toList();

                if (!weeklySessions.isEmpty()) {
                    emailService.sendWeeklyReport(email, weeklySessions);
                }
            }

            System.out.println("Weekly reports sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending weekly reports: " + e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 9 1 * ?")
    public void sendMonthlyReports() {
        System.out.println("Starting monthly report generation...");

        try {
            Set<String> userEmails = getAllUserEmails();
            LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

            for (String email : userEmails) {
                List<Session> allSessions = sessionService.getAllSessions(email);

                List<Session> monthlySessions = allSessions.stream()
                        .filter(s -> s.getCreatedAt().isAfter(oneMonthAgo))
                        .toList();

                if (!monthlySessions.isEmpty()) {
                    emailService.sendMonthlyReport(email, monthlySessions);
                }
            }

            System.out.println("Monthly reports sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending monthly reports: " + e.getMessage());
        }
    }


    @Scheduled(cron = "0 0 10 * * ?")
    public void sendInactivityReminders() {
        System.out.println("Checking for inactive users...");

        try {
            Set<String> userEmails = getAllUserEmails();
            LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);

            for (String email : userEmails) {
                List<Session> allSessions = sessionService.getAllSessions(email);

                if (allSessions.isEmpty()) {
                    emailService.sendInactivityReminder(email, null);
                } else {
                    LocalDateTime lastSessionDate = allSessions.stream()
                            .map(Session::getCreatedAt)
                            .max(LocalDateTime::compareTo)
                            .orElse(null);

                    if (lastSessionDate != null && lastSessionDate.isBefore(twoWeeksAgo)) {
                        emailService.sendInactivityReminder(email, lastSessionDate);
                    }
                }
            }

            System.out.println("Inactivity reminders processed!");
        } catch (Exception e) {
            System.err.println("Error sending inactivity reminders: " + e.getMessage());
        }
    }

    private Set<String> getAllUserEmails() {
        Set<String> emails = new HashSet<>();

        try {
            Firestore db = getFirestore();
            List<QueryDocumentSnapshot> documents = db.collection("users")
                    .get()
                    .get()
                    .getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                emails.add(document.getId()); // Document ID is the email
            }
        } catch (Exception e) {
            System.err.println("Error fetching user emails: " + e.getMessage());
        }

        return emails;
    }
}