package com.ncu.college.verification.dto;

public class DocumentSummaryDto {

    private int id;                  // Document ID
    private String title;            // Document title
    private String description;      // Document description

    // Verification fields
    private String verificationStatus;  // e.g., "PENDING", "VERIFIED", "REJECTED"
    private String verifiedBy;         // Name of verifier
    private String remarks;            // Optional remarks

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus = verificationStatus; }

    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
