package com.ncu.college.verification.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Verification Service running on port 8003!";
    }
    // GET http://localhost:8003/verification/status/{docId}
    // Example: http://localhost:8003/verification/status/123
    @GetMapping("/status/{docId}")
    public String getVerificationStatus(@PathVariable String docId) {
        return "Verification status for Document ID: " + docId;
    }

    // POST http://localhost:8003/verification/start
    // Example Body: { "docId": "123", "type": "passport" }
    @PostMapping("/start")
    public String startVerification(@RequestBody Map<String, String> payload) {
        return "Started verification with data: " + payload.toString();
    }

    // PUT http://localhost:8003/verification/{docId}
    // Example: http://localhost:8003/verification/123
    // Example Body: { "status": "verified" }
    @PutMapping("/{docId}")
    public String updateVerification(@PathVariable String docId, @RequestBody Map<String, String> payload) {
        return "Updated verification for Document ID: " + docId + " with: " + payload.toString();
    }

    // DELETE http://localhost:8003/verification/{docId}
    // Example: http://localhost:8003/verification/123
    @DeleteMapping("/{docId}")
    public ResponseEntity<String> deleteVerification(@PathVariable String docId) {
        return ResponseEntity.ok("Deleted verification record for Document ID: " + docId);
    }

    // GET http://localhost:8003/verification/delete/{docId}
    // Example: http://localhost:8003/verification/delete/123
    @GetMapping("/delete/{docId}")
    public ResponseEntity<String> deleteViaGet(@PathVariable String docId) {
        return ResponseEntity.ok("Deleted verification record for Document ID: " + docId);
    }
}
