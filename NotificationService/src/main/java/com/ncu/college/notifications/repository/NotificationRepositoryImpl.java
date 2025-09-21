package com.ncu.college.notifications.repository;

import com.ncu.college.notifications.irepository.INotificationRepository;
import com.ncu.college.notifications.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public class NotificationRepositoryImpl implements INotificationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NotificationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Notification> getAllNotifications() {
        String sql = "SELECT * FROM notification";
        return jdbcTemplate.query(sql, new NotificationRowMapper());
    }

    @Override
    public Notification getNotificationById(int id) {
        String sql = "SELECT * FROM notification WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new NotificationRowMapper(), id);
    }

    @Override
    public List<Notification> getNotificationsByUserId(int userId) {
        String sql = "SELECT * FROM notification WHERE user_id = ?";
        return jdbcTemplate.query(sql, new NotificationRowMapper(), userId);
    }

    @Override
    public void createNotification(Notification notification) {
        String sql = "INSERT INTO notification (user_id, message, type, status, sent_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                notification.getUserId(),
                notification.getMessage(),
                notification.getType(),
                notification.getStatus(),
                notification.getSentAt() != null ? notification.getSentAt() : new Timestamp(System.currentTimeMillis())
        );
    }

    @Override
    public void updateNotification(int id, Map<String, String> updates) {
        // For example, only updating message & status
        String sql = "UPDATE notification SET message = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                updates.getOrDefault("message", ""),
                updates.getOrDefault("status", ""),
                id
        );
    }

    @Override
    public void deleteNotification(int id) {
        String sql = "DELETE FROM notification WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Notification> getUnreadNotifications(int userId) {
        String sql = "SELECT * FROM notification WHERE user_id = ? AND status = 'unread'";
        return jdbcTemplate.query(sql, new NotificationRowMapper(), userId);
    }
}
