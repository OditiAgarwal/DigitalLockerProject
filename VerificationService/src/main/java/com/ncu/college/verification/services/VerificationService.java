package com.ncu.college.verification.services;

import com.ncu.college.verification.dto.VerificationDto;
import com.ncu.college.verification.irepository.IVerificationRepository;
import com.ncu.college.verification.models.Verification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class VerificationService {

    private final IVerificationRepository verificationRepository;
    private final ModelMapper modelMapper;
    private final RestClient documentRestClient;

    @Autowired
    public VerificationService(IVerificationRepository verificationRepository,
                               ModelMapper modelMapper,
                               RestClient.Builder restClientBuilder) {
        this.verificationRepository = verificationRepository;
        this.modelMapper = modelMapper;
        this.documentRestClient = restClientBuilder.baseUrl("http://localhost:8001/documents").build();
    }

    // Get all verifications with document info
    public List<VerificationDto> getAllVerifications() {
        List<Verification> verifications = verificationRepository.getAllVerifications();
        List<VerificationDto> dtos = new ArrayList<>();
        for (Verification v : verifications) {
            VerificationDto dto = modelMapper.map(v, VerificationDto.class);
            attachDocumentInfo(dto);
            dtos.add(dto);
        }
        return dtos;
    }

    public VerificationDto getVerificationById(int docId) {
        Verification verification = verificationRepository.getVerificationById(docId);
        VerificationDto dto = modelMapper.map(verification, VerificationDto.class);
        attachDocumentInfo(dto);
        return dto;
    }

    public String getVerificationStatus(int docId) {
        return verificationRepository.getVerificationStatus(docId);
    }

    public VerificationDto startVerification(VerificationDto dto) {
        Verification verification = modelMapper.map(dto, Verification.class);
        verificationRepository.startVerification(verification);
        attachDocumentInfo(dto);
        return dto;
    }

    public String updateVerification(int docId, Map<String, String> payload) {
        String status = payload.get("status");
        verificationRepository.updateVerification(docId, status);
        return "Updated verification for Document ID: " + docId + " with status: " + status;
    }

    public String deleteVerification(int docId) {
        verificationRepository.deleteVerification(docId);
        return "Deleted verification record for Document ID: " + docId;
    }

    // Helper to attach document info to verification
    private void attachDocumentInfo(VerificationDto dto) {
        try {
            Map<String, Object> doc = documentRestClient.get()
                    .uri("/{id}", dto.getDocId())
                    .retrieve()
                    .body(Map.class);

            if (doc != null) {
                dto.setDocumentTitle((String) doc.get("title"));
                dto.setDocumentDescription((String) doc.get("description"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching document info for docId " + dto.getDocId() + ": " + e.getMessage());
        }
    }
}
