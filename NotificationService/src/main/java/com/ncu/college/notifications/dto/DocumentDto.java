package com.ncu.college.notifications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class DocumentDto {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("filePath")
    private String filePath;

    @JsonProperty("fileType")
    private String fileType;

    @JsonProperty("uploadDate")
    private Date uploadDate;

    @JsonProperty("status")
    private String status;

    public DocumentDto() {}

    public DocumentDto(String title, String description, String filePath, String fileType, Date uploadDate, String status) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploadDate = uploadDate;
        this.status = status;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Date getUploadDate() { return uploadDate; }
    public void setUploadDate(Date uploadDate) { this.uploadDate = uploadDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}