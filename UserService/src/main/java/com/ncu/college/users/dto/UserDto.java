package com.ncu.college.users.dto;

import java.util.List;

public class UserDto {
    private int id;
    private String name;
    private String email;
    private String phone;

    // Only summaries, not full details
    private List<DocumentSummaryDto> documents;
    private List<NotificationSummaryDto> notifications;

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<DocumentSummaryDto> getDocuments() {
        return documents;
    }
    public void setDocuments(List<DocumentSummaryDto> documents) {
        this.documents = documents;
    }

    public List<NotificationSummaryDto> getNotifications() {
        return notifications;
    }
    public void setNotifications(List<NotificationSummaryDto> notifications) {
        this.notifications = notifications;
    }
}
