package com.ncu.college.verification.dto;

import java.sql.Timestamp;

public class VerificationDto {
    private int id;
    private int docId;
    private String verifiedBy;
    private String verificationStatus;
    private Timestamp verificationDate;
    private String remarks;

    // Additional: document info
    private String documentTitle;
    private String documentDescription;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDocId() { return docId; }
    public void setDocId(int docId) { this.docId = docId; }

    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }

    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus = verificationStatus; }

    public Timestamp getVerificationDate() { return verificationDate; }
    public void setVerificationDate(Timestamp verificationDate) { this.verificationDate = verificationDate; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getDocumentTitle() { return documentTitle; }
    public void setDocumentTitle(String documentTitle) { this.documentTitle = documentTitle; }

    public String getDocumentDescription() { return documentDescription; }
    public void setDocumentDescription(String documentDescription) { this.documentDescription = documentDescription; }
}
