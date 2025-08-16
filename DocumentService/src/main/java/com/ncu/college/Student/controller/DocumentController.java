package com.ncu.college.documents.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;


@RestController
@RequestMapping("/documents")
public class DocumentController {



     @GetMapping("/hello")
    public String hello() {
        return "Hello from Document Service running on port 8001!";
    }
    // ================================
    // GET /documents — List all documents
    // Example: http://localhost:8001/documents
    // ================================
    @GetMapping
    public List<String> getAllDocuments() {
        return Arrays.asList("Doc1", "Doc2", "Doc3");
    }

    // ================================
    // GET /documents/{id} — Get document by ID
    // Example: http://localhost:8001/documents/101
    // ================================
    @GetMapping("/{id}")
    public String getDocumentById(@PathVariable String id) {
        return "Document with ID: " + id;
    }

    // ================================
    // GET /documents/user/{userId} — Get all documents by user ID
    // Example: http://localhost:8001/documents/user/201
    // ================================
    @GetMapping("/user/{userId}")
    public String getDocumentsByUserId(@PathVariable String userId) {
        return "Documents for User ID: " + userId;
    }

    // ================================
    // POST /documents — Upload new document
    // Example: POST http://localhost:8001/documents
    // Body (form-data): file=<file>, title=Sample
    // ================================
    @PostMapping
    public String uploadDocument(@RequestParam("file") MultipartFile file,
                                 @RequestParam("title") String title) {
        return "Uploaded: " + title + " (" + file.getOriginalFilename() + ")";
    }

    // ================================
    // PUT /documents/{id} — Update document metadata
    // Example: PUT http://localhost:8001/documents/101
    // Body (JSON): { "title": "Updated Title" }
    // ================================
    @PutMapping("/{id}")
    public String updateDocument(@PathVariable String id, @RequestBody Map<String, String> metadata) {
        return "Updated Document ID: " + id + " with metadata: " + metadata.toString();
    }

    // ================================
    // DELETE /documents/delete/{id} — Delete document
    // Example: DELETE http://localhost:8001/documents/delete/101
    // ================================
  // DELETE via REST client (Postman/curl)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable String id) {
        System.out.println("Deleting document with ID: " + id);
        return ResponseEntity.ok("Deleted Document ID: " + id);
    }

    // DELETE via browser (for quick test only)
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteViaGet(@PathVariable String id) {
        System.out.println("Deleting document via GET with ID: " + id);
        return ResponseEntity.ok("Deleted Document (GET) ID: " + id);
    }

    // ================================
    // GET /documents/{id}/download — Download document file
    // Example: http://localhost:8001/documents/101/download
    // ================================
    @GetMapping("/{id}/download")
    public String downloadDocument(@PathVariable String id) {
        return "Downloading file for Document ID: " + id;
    }

    // ================================
    // GET /documents/search?title={title} — Search documents by title
    // Example: http://localhost:8001/documents/search?title=Sample
    // ================================
    @GetMapping("/search")
    public String searchDocumentsByTitle(@RequestParam String title) {
        return "Search results for title: " + title;
    }
}
