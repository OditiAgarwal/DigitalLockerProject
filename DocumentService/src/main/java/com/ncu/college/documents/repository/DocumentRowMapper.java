package com.ncu.college.documents.repository;

import com.ncu.college.documents.model.Document;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentRowMapper implements RowMapper<Document> {
@Override
public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
    Document doc = new Document();
    doc.setId(rs.getInt("id"));
    doc.setUserId(rs.getInt("user_id"));
    doc.setTitle(rs.getString("title"));
    doc.setDescription(rs.getString("description"));
    doc.setFilePath(rs.getString("file_path"));
    doc.setFileType(rs.getString("file_type"));
    doc.setUploadDate(rs.getTimestamp("upload_date"));
    
    return doc;
}

}
