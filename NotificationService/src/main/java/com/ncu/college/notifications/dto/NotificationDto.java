package com.ncu.college.notifications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationDto {

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    public NotificationDto() {}

    public NotificationDto(int userId, String message, String type, String status) {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.status = status;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
