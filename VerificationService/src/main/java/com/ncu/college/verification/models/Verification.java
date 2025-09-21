package com.ncu.college.verification.models;

import java.sql.Timestamp;

public class Verification {
    private int id;
    private int docId;
    private String verifiedBy;
    private String verificationStatus;
    private Timestamp verificationDate;
    private String remarks;

    // Full constructor
    public Verification(int id, int docId, String verifiedBy, String verificationStatus,
                        Timestamp verificationDate, String remarks) {
        this.id = id;
        this.docId = docId;
        this.verifiedBy = verifiedBy;
        this.verificationStatus = verificationStatus;
        this.verificationDate = verificationDate;
        this.remarks = remarks;
    }

    // Default constructor
    public Verification() {}

    // Getters
    public int getId() {
        return id;
    }

    public int getDocId() {
        return docId;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public Timestamp getVerificationDate() {
        return verificationDate;
    }

    public String getRemarks() {
        return remarks;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public void setVerificationDate(Timestamp verificationDate) {
        this.verificationDate = verificationDate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
