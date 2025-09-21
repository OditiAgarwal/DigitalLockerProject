package com.ncu.college.documents.model;

import java.util.Date;

public class Document {

    private int id;
    private int userId;
    private String title;
    private String description;
    private String filePath;
    private String fileType;
    private Date uploadDate;
   

    public Document() {}

    public Document(int id, int userId, String title, String description, String filePath, String fileType, Date uploadDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploadDate = uploadDate;
      
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

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

 
}
