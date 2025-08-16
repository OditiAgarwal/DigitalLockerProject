package com.ncu.college.notifications.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {



     @GetMapping("/hello")
    public String hello() {
        return "Hello from Notification Service running on port 8002!";
    }
    // 
    // GET /notifications — List all notifications (admin)
    // Example: http://localhost:8002/notifications
    // 
    @GetMapping
    public List<String> getAllNotifications() {
        return Arrays.asList("Notification1", "Notification2", "Notification3");
    }

    // .
    // GET /notifications/user/{userId} — List notifications for a user
    // Example: http://localhost:8002/notifications/user/5
    // .
    @GetMapping("/user/{userId}")
    public String getNotificationsByUserId(@PathVariable String userId) {
        return "Notifications for User ID: " + userId;
    }
// POST /notifications — Create/send notification
// Example: POST http://localhost:8002/notifications
@PostMapping
public String createNotification(@RequestBody Map<String, String> payload) {
    return "Created notification with data: " + payload.toString();
}


    // PUT /notifications/{id} — Update notification status or message
    // Example: PUT http://localhost:8002/notifications/3
    // 
    @PutMapping("/{id}")
    public String updateNotification(@PathVariable String id, @RequestBody Map<String, String> payload) {
        return "Updated Notification ID: " + id + " with: " + payloadtoString();
    }

    // 
    // DELETE /notifications/{id} — Delete notification
    // Example: DELETE http://localhost:8002/notifications/3
    // 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable String id) {
        return ResponseEntityok("Deleted Notification ID: " + id);
    }

    // DELETE via browser (quick test only)
    // Example: http://localhost:8002/notifications/delete/3
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteViaGet(@PathVariable String id) {
        return ResponseEntityok("Deleted Notification ID: " + id);
    }

    // 
    // GET /notifications/unread/user/{userId} — Get unread notifications for a user
    // Example: http://localhost:8002/notifications/unread/user/5
    
    @GetMapping("/unread/user/{userId}")
    public String getUnreadNotifications(@PathVariable String userId) {
        return "Unread notifications for User ID: " + userId;
    }
}
