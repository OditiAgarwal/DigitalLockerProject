package com.ncu.college.notifications.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;
import com.ncu.college.notifications.models.Notification;
public class NotificationRowMapper implements RowMapper<Notification> {

    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {

        if (rs == null) {
            return null;
        }

        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        String message = rs.getString("message");
        String type = rs.getString("type");
        String status = rs.getString("status");
        Timestamp sentAt = rs.getTimestamp("sent_at");

        return new Notification(id, userId, message, type, status, sentAt);
    }
}
