package com.productivity.authapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.productivity.authapp.model.Session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWeeklyReport(String userEmail, List<Session> weeklySessions) {
        if (weeklySessions.isEmpty()) {
            return;
        }

        int totalSessions = weeklySessions.size();
        int totalMinutes = weeklySessions.stream().mapToInt(Session::getDuration).sum();
        double avgRating = weeklySessions.stream().mapToInt(Session::getRating).average().orElse(0.0);

        String subject = "📊 Your Weekly Study Report";
        String body = String.format(
                "Hello!\n\n" +
                        "Here's your weekly productivity summary:\n\n" +
                        "✅ Total Sessions: %d\n" +
                        "⏱️ Total Study Time: %dh %dm\n" +
                        "⭐ Average Rating: %.1f/5.0\n\n" +
                        "Keep up the great work!\n\n" +
                        "Best regards,\n" +
                        "Productivity Tracker Team",
                totalSessions,
                totalMinutes / 60,
                totalMinutes % 60,
                avgRating
        );

        sendEmail(userEmail, subject, body);
    }

    public void sendMonthlyReport(String userEmail, List<Session> monthlySessions) {
        if (monthlySessions.isEmpty()) {
            return;
        }

        int totalSessions = monthlySessions.size();
        int totalMinutes = monthlySessions.stream().mapToInt(Session::getDuration).sum();
        double avgRating = monthlySessions.stream().mapToInt(Session::getRating).average().orElse(0.0);

        String mostStudiedSubject = monthlySessions.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Session::getSubject,
                        java.util.stream.Collectors.summingInt(Session::getDuration)
                ))
                .entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse("N/A");

        String subject = "📈 Your Monthly Study Report";
        String body = String.format(
                "Hello!\n\n" +
                        "Here's your monthly productivity summary:\n\n" +
                        "✅ Total Sessions: %d\n" +
                        "⏱️ Total Study Time: %dh %dm\n" +
                        "⭐ Average Rating: %.1f/5.0\n" +
                        "📚 Most Studied Subject: %s\n\n" +
                        "Excellent progress this month!\n\n" +
                        "Best regards,\n" +
                        "Productivity Tracker Team",
                totalSessions,
                totalMinutes / 60,
                totalMinutes % 60,
                avgRating,
                mostStudiedSubject
        );

        sendEmail(userEmail, subject, body);
    }

    public void sendInactivityReminder(String userEmail, LocalDateTime lastSessionDate) {
        String subject = "⏰ Time to Study! We Miss You";

        String lastSessionInfo = lastSessionDate != null
                ? "Your last study session was on " + lastSessionDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                : "You haven't logged any study sessions yet";

        String body = String.format(
                "Hello!\n\n" +
                        "We noticed you haven't logged a study session in the past two weeks.\n\n" +
                        "%s.\n\n" +
                        "📚 Keep your learning momentum going!\n" +
                        "💪 Even a short study session makes a difference\n" +
                        "🎯 Log into your Productivity Tracker and start a new session\n\n" +
                        "Remember: Consistency is key to success!\n\n" +
                        "Best regards,\n" +
                        "Productivity Tracker Team",
                lastSessionInfo
        );

        sendEmail(userEmail, subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("tunabieber69420@gmail.com");

            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Error sending email to " + to + ": " + e.getMessage());
        }
    }
}