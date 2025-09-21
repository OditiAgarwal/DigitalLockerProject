package com.ncu.college.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

import com.ncu.college.notifications.dto.NotificationDto;
import com.ncu.college.notifications.services.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // ================================
    // GET /notifications — List all notifications (admin)
    // Example: http://localhost:8002/notifications
    // ================================
    @GetMapping
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // ================================
    // GET /notifications/{id} — Get notification by ID
    // Example: http://localhost:8002/notifications/5
    // ================================
    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable("id") int id) {
        return notificationService.getNotificationById(id);
    }

    // ================================
    // GET /notifications/user/{userId} — List notifications for a user
    // Example: http://localhost:8002/notifications/user/3
    // ================================
    @GetMapping("/user/{userId}")
    public List<NotificationDto> getNotificationsByUserId(@PathVariable("userId") int userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    // ================================
    // POST /notifications — Create/send notification
    // Example: POST http://localhost:8002/notifications
    // ================================
    @PostMapping
    public NotificationDto createNotification(@RequestBody NotificationDto notificationDto) {
        return notificationService.createNotification(notificationDto);
    }

    // ================================
    // PUT /notifications/{id} — Update notification
    // Example: PUT http://localhost:8002/notifications/7
    // ================================
    @PutMapping("/{id}")
    public String updateNotification(@PathVariable("id") int id,
                                     @RequestBody Map<String, String> updates) {
        return notificationService.updateNotification(id, updates);
    }

    // ================================
    // DELETE /notifications/{id} — Delete notification
    // Example: DELETE http://localhost:8002/notifications/9
    // ================================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") int id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }

    // ================================
    // GET /notifications/unread/user/{userId} — Get unread notifications
    // Example: http://localhost:8002/notifications/unread/user/3
    // ================================
    @GetMapping("/unread/user/{userId}")
    public List<NotificationDto> getUnreadNotifications(@PathVariable("userId") int userId) {
        return notificationService.getUnreadNotifications(userId);
    }


    // DELETE all notifications for a user
@DeleteMapping("/user/{userId}")
public ResponseEntity<String> deleteNotificationsByUser(@PathVariable("userId") int userId) {
    notificationService.deleteNotificationsByUserId(userId);
    return ResponseEntity.ok("Deleted all notifications for user " + userId);
}

}
