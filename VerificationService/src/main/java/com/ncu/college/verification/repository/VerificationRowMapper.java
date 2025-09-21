package com.ncu.college.verification.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;
import com.ncu.college.verification.models.Verification;

public class VerificationRowMapper implements RowMapper<Verification> {
    @Override
    public Verification mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Verification(
            rs.getInt("id"),
            rs.getInt("doc_id"),
            rs.getString("verified_by"),
            rs.getString("verification_status"),
            rs.getTimestamp("verification_date"),
            rs.getString("remarks")
        );
    }
}
