package com.ncu.college.documents.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import com.ncu.college.documents.dto.DocumentDto;
import com.ncu.college.documents.dto.DocumentResponseDto;
import com.ncu.college.documents.dto.VerificationDto;
import com.ncu.college.documents.dto.UserSummaryDto;
import com.ncu.college.documents.irepository.IDocumentRepository;
import com.ncu.college.documents.model.Document;

@Service(value = "DocumentService")
public class DocumentService {

    private final IDocumentRepository documentRepository;
    private final ModelMapper modelMapper;

    private final RestClient verificationRestClient;
    private final RestClient userRestClient;

    @Autowired
    public DocumentService(IDocumentRepository repo, ModelMapper mapper, RestClient.Builder builder) {
        this.documentRepository = repo;
        this.modelMapper = mapper;

        this.verificationRestClient = builder.baseUrl("http://localhost:8003/verification").build();
        this.userRestClient = builder.baseUrl("http://localhost:8000/users").build();
    }

    // ---------------- Get single document (detailed) ----------------
    public DocumentResponseDto getDocumentById(String id) {
        int docId = Integer.parseInt(id);
        Document doc = documentRepository.getDocumentById(docId);
        if (doc == null) {
            throw new RuntimeException("Document not found with ID: " + docId);
        }
        return mapToResponseDto(doc);
    }

    // ---------------- List all documents (detailed) ----------------
    public List<DocumentResponseDto> getAllDocuments() {
        List<Document> documents = documentRepository.getAllDocuments();
        List<DocumentResponseDto> responses = new ArrayList<>();
        for (Document doc : documents) {
            responses.add(mapToResponseDto(doc));
        }
        return responses;
    }

    // ---------------- Documents by User (for UserService) ----------------
    public List<DocumentDto> getDocumentsByUserId(String userId) {
        int uid = Integer.parseInt(userId);
        List<Document> documents = documentRepository.getDocumentsByUserId(uid);
        List<DocumentDto> dtos = new ArrayList<>();
        for (Document doc : documents) {
            dtos.add(modelMapper.map(doc, DocumentDto.class));
        }
        return dtos;
    }

    // ---------------- Upload document ----------------
    public String uploadDocument(MultipartFile file, String title, int userId) {
        Document doc = new Document();
        doc.setUserId(userId);
        doc.setTitle(title);
        doc.setDescription("Uploaded file: " + file.getOriginalFilename());

        Document saved = documentRepository.addDocument(doc);

        // create verification entry
        try {
            VerificationDto v = new VerificationDto();
            v.setDocId(saved.getId());
            v.setVerificationStatus("pending");
            verificationRestClient.post()
                    .uri("")
                    .body(v)
                    .retrieve()
                    .body(Void.class);
        } catch (Exception e) {
            System.out.println("Warning: verification entry not created for doc " + saved.getId());
        }

        return "Uploaded document with ID: " + saved.getId();
    }

    // ---------------- Update document ----------------
    public String updateDocument(String id, Map<String, String> metadata) {
        int docId = Integer.parseInt(id);
        Document existing = documentRepository.getDocumentById(docId);
        if (existing == null) {
            throw new RuntimeException("Document not found with ID: " + docId);
        }

        existing.setTitle(metadata.getOrDefault("title", existing.getTitle()));
        existing.setDescription(metadata.getOrDefault("description", existing.getDescription()));
        documentRepository.updateDocument(existing);

        return "Updated Document ID: " + id;
    }

    // ---------------- Delete document ----------------
    public String deleteDocument(String id) {
        int docId = Integer.parseInt(id);
        boolean deleted = documentRepository.deleteDocument(docId);
        if (!deleted) {
            throw new RuntimeException("Failed to delete document with ID: " + docId);
        }

        try {
            verificationRestClient.delete().uri("/{id}", docId).retrieve().body(Void.class);
        } catch (Exception e) {
            System.out.println("Warning: could not delete verification entry for doc " + docId);
        }

        return "Deleted Document ID: " + id;
    }

    // ---------------- Download (mock) ----------------
    public String downloadDocument(String id) {
        return "Downloading file for Document ID: " + id;
    }

    // ---------------- Search documents (detailed) ----------------
    public List<DocumentResponseDto> searchDocumentsByTitle(String title) {
        List<Document> documents = documentRepository.searchDocumentsByTitle(title);
        List<DocumentResponseDto> responses = new ArrayList<>();
        for (Document doc : documents) {
            responses.add(mapToResponseDto(doc));
        }
        return responses;
    }

    // ---------------- Helper: enrich response ----------------
    private DocumentResponseDto mapToResponseDto(Document doc) {
        DocumentResponseDto dto = new DocumentResponseDto();
        dto.setId(doc.getId());
        dto.setTitle(doc.getTitle());
        dto.setDescription(doc.getDescription());

        // verification
        dto.setStatus(fetchVerificationStatus(doc.getId()));

        // user
        dto.setUser(fetchUser(doc.getUserId()));

        return dto;
    }

    private String fetchVerificationStatus(int docId) {
        try {
            VerificationDto verification = verificationRestClient.get()
                    .uri("/{docId}", docId)
                    .retrieve()
                    .body(VerificationDto.class);
            return verification != null ? verification.getVerificationStatus() : "unknown";
        } catch (Exception e) {
            return "unknown";
        }
    }

    private UserSummaryDto fetchUser(int userId) {
        try {
            return userRestClient.get()
                    .uri("/{id}", userId)
                    .retrieve()
                    .body(UserSummaryDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
