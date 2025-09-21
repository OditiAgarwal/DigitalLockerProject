package com.ncu.college.notifications.services;

import com.ncu.college.notifications.dto.NotificationDto;
import com.ncu.college.notifications.irepository.INotificationRepository;
import com.ncu.college.notifications.models.Notification;
import com.ncu.college.notifications.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.*;

@Service
public class NotificationService {

    private final INotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    private final RestClient userRestClient;

    @Autowired
    public NotificationService(INotificationRepository repo, ModelMapper mapper, RestClient.Builder builder) {
        this.notificationRepository = repo;
        this.modelMapper = mapper;
        this.userRestClient = builder.baseUrl("http://localhost:8000/users").build();
    }

    // Create a notification for a specific user
    public NotificationDto createNotification(NotificationDto dto) {
        // Verify user exists
        try {
            userRestClient.get()
                          .uri("/{id}", dto.getUserId())
                          .retrieve()
                          .body(UserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("User does not exist");
        }

        Notification notification = modelMapper.map(dto, Notification.class);
        if (notification.getStatus() == null) {
            notification.setStatus("unread"); // default status
        }
        notification.setSentAt(new java.sql.Timestamp(System.currentTimeMillis()));

        notificationRepository.createNotification(notification);
        return dto;
    }

    // Fetch all notifications for a user
    public List<NotificationDto> getNotificationsByUserId(int userId) {
        List<Notification> notifications = notificationRepository.getNotificationsByUserId(userId);
        List<NotificationDto> dtos = new ArrayList<>();
        for (Notification n : notifications) {
            dtos.add(modelMapper.map(n, NotificationDto.class));
        }
        return dtos;
    }

    // Fetch unread notifications
    public List<NotificationDto> getUnreadNotifications(int userId) {
        List<Notification> notifications = notificationRepository.getUnreadNotifications(userId);
        List<NotificationDto> dtos = new ArrayList<>();
        for (Notification n : notifications) {
            dtos.add(modelMapper.map(n, NotificationDto.class));
        }
        return dtos;
    }

    // Delete all notifications for a user (used when user is deleted)
    public void deleteNotificationsByUserId(int userId) {
        List<Notification> notifications = notificationRepository.getNotificationsByUserId(userId);
        for (Notification n : notifications) {
            notificationRepository.deleteNotification(n.getId());
        }
    }

    // Existing methods for admin or single notification management
    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.getAllNotifications();
        List<NotificationDto> dtos = new ArrayList<>();
        for (Notification notification : notifications) {
            dtos.add(modelMapper.map(notification, NotificationDto.class));
        }
        return dtos;
    }

    public NotificationDto getNotificationById(int id) {
        Notification notification = notificationRepository.getNotificationById(id);
        return modelMapper.map(notification, NotificationDto.class);
    }

    public String updateNotification(int id, Map<String, String> updates) {
        notificationRepository.updateNotification(id, updates);
        return "Notification " + id + " updated successfully.";
    }

    public String deleteNotification(int id) {
        notificationRepository.deleteNotification(id);
        return "Notification " + id + " deleted successfully.";
    }
}
