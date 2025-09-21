package com.ncu.college.notifications.models;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private int userId;
    private String message;
    private String type;
    private String status;
    private Timestamp sentAt;

    // Default constructor (needed for frameworks)
    public Notification() {}

    // Full constructor
    public Notification(int id, int userId, String message, String type, String status, Timestamp sentAt) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.status = status;
        this.sentAt = sentAt;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getSentAt() { return sentAt; }
    public void setSentAt(Timestamp sentAt) { this.sentAt = sentAt; }
}
