package com.ncu.college.documents.irepository;

import com.ncu.college.documents.model.Document;
import java.util.List;

public interface IDocumentRepository {
    List<Document> getAllDocuments();
    Document getDocumentById(int id);
    List<Document> getDocumentsByUserId(int userId);
    Document addDocument(Document document);
    Document updateDocument(Document document);
    boolean deleteDocument(int id);
    List<Document> searchDocumentsByTitle(String title);
}
