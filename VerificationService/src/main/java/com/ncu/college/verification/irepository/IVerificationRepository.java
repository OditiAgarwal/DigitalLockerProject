package com.ncu.college.verification.irepository;

import com.ncu.college.verification.models.Verification;
import java.util.List;

public interface IVerificationRepository {

    List<Verification> getAllVerifications();
    Verification getVerificationById(int docId);

    
    String getVerificationStatus(int docId);

    void startVerification(Verification verification);

    void updateVerification(int docId, String status);

    void deleteVerification(int docId);
}
