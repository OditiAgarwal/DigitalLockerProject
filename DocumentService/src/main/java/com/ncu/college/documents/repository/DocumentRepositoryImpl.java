package com.ncu.college.documents.repository;

import com.ncu.college.documents.irepository.IDocumentRepository;
import com.ncu.college.documents.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("DocumentRepositoryImpl")
public class DocumentRepositoryImpl implements IDocumentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Document> getAllDocuments() {
        return jdbcTemplate.query("SELECT * FROM document", new DocumentRowMapper());
    }

    @Override
    public Document getDocumentById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM document WHERE id = ?", new DocumentRowMapper(), id);
    }

    @Override
    public List<Document> getDocumentsByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM document WHERE user_id = ?", new DocumentRowMapper(), userId);
    }

    @Override
    public Document addDocument(Document document) {
        String sql = "INSERT INTO document (user_id, title, description, file_path, file_type, upload_date) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, document.getUserId(), document.getTitle(), document.getDescription(),
                document.getFilePath(), document.getFileType(), document.getUploadDate());
        return document;
    }

    @Override
    public Document updateDocument(Document document) {
        String sql = "UPDATE document SET title=?, description=?, file_path=?, file_type=? WHERE id=?";
        jdbcTemplate.update(sql, document.getTitle(), document.getDescription(), document.getFilePath(),
                document.getFileType(),document.getId());
        return document;
    }

    @Override
    public boolean deleteDocument(int id) {
        String sql = "DELETE FROM document WHERE id=?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public List<Document> searchDocumentsByTitle(String title) {
        return jdbcTemplate.query("SELECT * FROM document WHERE title LIKE ?", new DocumentRowMapper(), "%" + title + "%");
    }
}
