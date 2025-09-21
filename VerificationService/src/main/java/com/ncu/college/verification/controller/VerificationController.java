package com.ncu.college.verification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ncu.college.verification.dto.VerificationDto;
import com.ncu.college.verification.services.VerificationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    private final VerificationService verificationService;

    @Autowired
    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    // ================================
    // GET /verification — List all verifications
    // Example: http://localhost:8003/verification
    // ================================
    @GetMapping
    public List<VerificationDto> getAllVerifications() {
        return verificationService.getAllVerifications();
    }

    // ================================
    // GET /verification/{docId} — Get verification by document ID
    // Example: http://localhost:8003/verification/1
    // ================================
    @GetMapping("/{docId}")
    public VerificationDto getVerificationById(@PathVariable("docId") int docId) {
        return verificationService.getVerificationById(docId);
    }

    // ================================
    // GET /verification/status/{docId} — Get verification status by document ID
    // Example: http://localhost:8003/verification/status/1
    // ================================
    @GetMapping("/status/{docId}")
    public String getVerificationStatus(@PathVariable("docId") int docId) {
        return verificationService.getVerificationStatus(docId);
    }

    // ================================
    // POST /verification — Start new verification
    // Example: POST http://localhost:8003/verification
    // ================================
    @PostMapping
    public VerificationDto startVerification(@RequestBody VerificationDto verificationDto) {
        return verificationService.startVerification(verificationDto);
    }

    // ================================
    // PUT /verification/{docId} — Update verification status
    // Example: PUT http://localhost:8003/verification/1
    // ================================
    @PutMapping("/{docId}")
    public String updateVerification(@PathVariable("docId") int docId,
                                     @RequestBody Map<String, String> payload) {
        return verificationService.updateVerification(docId, payload);
    }

    // ================================
    // DELETE /verification/{docId} — Delete verification record
    // Example: DELETE http://localhost:8003/verification/1
    // ================================
    @DeleteMapping("/{docId}")
    public ResponseEntity<String> deleteVerification(@PathVariable("docId") int docId) {
        return ResponseEntity.ok(verificationService.deleteVerification(docId));
    }
}
