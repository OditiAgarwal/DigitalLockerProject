package com.ncu.college.documents.dto;

public class VerificationDto {
 private int id;
    private int docId;
    private String verifiedBy;
    private String verificationStatus;
    private String verificationDate;
    private String remarks;

    // Getters & Setters
    public int getId() { return id; }   
    public void setId(int id) { this.id = id; }
    public int getDocId() { return docId; }
    public void setDocId(int docId) { this.docId = docId; }
    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }
    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus = verificationStatus; }
    public String getVerificationDate() { return verificationDate; }
    public void setVerificationDate(String verificationDate) { this.verificationDate = verificationDate; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
