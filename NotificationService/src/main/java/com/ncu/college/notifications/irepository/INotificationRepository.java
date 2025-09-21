package com.ncu.college.notifications.irepository;

import java.util.List;
import java.util.Map;
import com.ncu.college.notifications.models.Notification;

public interface INotificationRepository {

    // List all notifications
    List<Notification> getAllNotifications();

    // Get notification by ID
    Notification getNotificationById(int id);

    // Get notifications by user ID
    List<Notification> getNotificationsByUserId(int userId);

    // Add (create) a new notification
    void createNotification(Notification notification);

    // Update notification (status/message)
    void updateNotification(int id, Map<String, String> updates);

    // Delete a notification
    void deleteNotification(int id);

    // Get unread notifications by user ID
    List<Notification> getUnreadNotifications(int userId);
}
