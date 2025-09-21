package com.ncu.college.verification.repository;

import com.ncu.college.verification.irepository.IVerificationRepository;
import com.ncu.college.verification.models.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VerificationRepositoryImpl implements IVerificationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VerificationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Verification> getAllVerifications() {
        String sql = "SELECT * FROM verification";
        return jdbcTemplate.query(sql, new VerificationRowMapper());
    }

    @Override
    public Verification getVerificationById(int docId) {
        String sql = "SELECT * FROM verification WHERE doc_id = ?";
        return jdbcTemplate.queryForObject(sql, new VerificationRowMapper(), docId);
    }

    @Override
    public String getVerificationStatus(int docId) {
        String sql = "SELECT verification_status FROM verification WHERE doc_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, docId);
    }

    @Override
    public void startVerification(Verification verification) {
        String sql = "INSERT INTO verification (doc_id, verified_by, verification_status, verification_date, remarks) " +
                     "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                verification.getDocId(),
                verification.getVerifiedBy(),
                verification.getVerificationStatus(),
                verification.getVerificationDate(),
                verification.getRemarks()
        );
    }

    @Override
    public void updateVerification(int docId, String status) {
        String sql = "UPDATE verification SET verification_status = ? WHERE doc_id = ?";
        jdbcTemplate.update(sql, status, docId);
    }

    @Override
    public void deleteVerification(int docId) {
        String sql = "DELETE FROM verification WHERE doc_id = ?";
        jdbcTemplate.update(sql, docId);
    }
}
