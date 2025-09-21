package com.ncu.college.documents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import com.ncu.college.documents.dto.DocumentDto;
import com.ncu.college.documents.dto.DocumentResponseDto;
import com.ncu.college.documents.service.DocumentService;

@RequestMapping("/documents")
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // ------------------------- CRUD + Search -------------------------

    // GET /documents — List all documents (detailed, includes verification + user)
    // Example: http://localhost:8001/documents/
    @GetMapping("/")
    public List<DocumentResponseDto> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    // GET /documents/{id} — Get document by ID (detailed, includes verification + user)
    // Example: http://localhost:8001/documents/5
    @GetMapping("/{id}")
    public DocumentResponseDto getDocumentById(@PathVariable("id") String id) {
        return documentService.getDocumentById(id);
    }

    // GET /documents/user/{userId} — Get all documents uploaded by a specific user
    // NOTE: This returns DocumentDto (basic) so UserService can consume it
    // Example: http://localhost:8001/documents/user/2
    @GetMapping("/user/{userId}")
    public List<DocumentDto> getDocumentsByUserId(@PathVariable("userId") String userId) {
        return documentService.getDocumentsByUserId(userId);
    }

    // POST /documents — Upload a new document
    // Example: POST http://localhost:8001/documents/
    // Form-data: file=<uploadFile>, title="Driving License", userId=2
    @PostMapping("/")
    public String uploadDocument(@RequestParam("file") MultipartFile file,
                                 @RequestParam("title") String title,
                                 @RequestParam("userId") int userId) {
        return documentService.uploadDocument(file, title, userId);
    }

    // PUT /documents/{id} — Update document metadata
    // Example: PUT http://localhost:8001/documents/5
    // Body (JSON): { "title": "Updated Title", "description": "Updated description" }
    @PutMapping("/{id}")
    public String updateDocument(@PathVariable("id") String id,
                                 @RequestBody Map<String, String> metadata) {
        return documentService.updateDocument(id, metadata);
    }

    // DELETE /documents/{id} — Delete a document
    // Example: DELETE http://localhost:8001/documents/5
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("id") String id) {
        return ResponseEntity.ok(documentService.deleteDocument(id));
    }

    // GET /documents/{id}/download — Download a document file
    // Example: http://localhost:8001/documents/5/download
    @GetMapping("/{id}/download")
    public String downloadDocument(@PathVariable("id") String id) {
        return documentService.downloadDocument(id);
    }

    // GET /documents/search?title={title} — Search documents by title
    // Example: http://localhost:8001/documents/search?title=Aadhaar
    @GetMapping("/search")
    public List<DocumentResponseDto> searchDocumentsByTitle(@RequestParam("title") String title) {
        return documentService.searchDocumentsByTitle(title);
    }
}
