package com.ncu.college.users.services;

import com.ncu.college.users.dto.*;
import com.ncu.college.users.irepository.IUserRepository;
import com.ncu.college.users.models.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class UserService {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    private final RestClient documentRestClient;
    private final RestClient notificationRestClient;

    @Autowired
    public UserService(IUserRepository userRepository,
                       ModelMapper modelMapper,
                       RestClient.Builder restClientBuilder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

        this.documentRestClient = restClientBuilder.baseUrl("http://localhost:8001/documents").build();
        this.notificationRestClient = restClientBuilder.baseUrl("http://localhost:8002/notifications").build();
    }

    // ------------------- CRUD + Enrichment -------------------

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            UserDto udto = modelMapper.map(user, UserDto.class);
            udto.setDocuments(getDocumentSummaries(user.getId()));
            udto.setNotifications(getNotificationSummaries(user.getId()));
            dtos.add(udto);
        }
        return dtos;
    }

    public UserDto getUserById(int id) {
        User user = userRepository.getUserById(id);
        UserDto udto = modelMapper.map(user, UserDto.class);
        udto.setDocuments(getDocumentSummaries(id));
        udto.setNotifications(getNotificationSummaries(id));
        return udto;
    }

    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User created = userRepository.createUser(user);

        // Create documents in Document Service
        if (userDto.getDocuments() != null && !userDto.getDocuments().isEmpty()) {
            for (DocumentSummaryDto d : userDto.getDocuments()) {
                try {
                    DocumentDto fullDoc = new DocumentDto();
                    fullDoc.setUserId(created.getId());
                    fullDoc.setTitle(d.getTitle());
                    fullDoc.setDescription(d.getDescription());

                    documentRestClient.post()
                            .uri("")
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fullDoc)
                            .retrieve()
                            .body(Void.class);
                } catch (Exception e) {
                    System.out.println("Error creating document for user " + created.getId() + ": " + e.getMessage());
                }
            }
        }

        // Send welcome notification
        try {
            NotificationDto welcome = new NotificationDto();
            welcome.setUserId(created.getId());
            welcome.setMessage("Welcome " + created.getName() + " to DigiLocker!");
            welcome.setType("SYSTEM");

            notificationRestClient.post()
                    .uri("")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(welcome)
                    .retrieve()
                    .body(Void.class);
        } catch (Exception e) {
            System.out.println("Error sending welcome notification for user " + created.getId() + ": " + e.getMessage());
        }

        UserDto result = modelMapper.map(created, UserDto.class);
        result.setDocuments(getDocumentSummaries(created.getId()));
        result.setNotifications(getNotificationSummaries(created.getId()));
        return result;
    }

    public UserDto updateUser(int id, UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        userRepository.updateUser(id, user);

        UserDto udto = modelMapper.map(user, UserDto.class);
        udto.setDocuments(getDocumentSummaries(id));
        udto.setNotifications(getNotificationSummaries(id));
        return udto;
    }

    public String updatePassword(int id, String newPassword) {
        userRepository.updatePassword(id, newPassword);

        try {
            NotificationDto notif = new NotificationDto();
            notif.setUserId(id);
            notif.setMessage("Your password was updated successfully.");
            notif.setType("SECURITY");

            notificationRestClient.post()
                    .uri("")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(notif)
                    .retrieve()
                    .body(Void.class);
        } catch (Exception e) {
            System.out.println("Error sending password update notification for user " + id + ": " + e.getMessage());
        }

        return "Password updated for User ID: " + id;
    }

    public List<UserDto> searchUsersByName(String name) {
        List<User> users = userRepository.searchUsersByName(name);
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            UserDto udto = modelMapper.map(user, UserDto.class);
            udto.setDocuments(getDocumentSummaries(user.getId()));
            udto.setNotifications(getNotificationSummaries(user.getId()));
            dtos.add(udto);
        }
        return dtos;
    }

    // ------------------- Helpers: summaries for API -------------------

    private List<DocumentSummaryDto> getDocumentSummaries(int userId) {
        try {
            List<DocumentDto> docs = documentRestClient.get()
                    .uri("/user/{userId}", userId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<DocumentDto>>() {});

            List<DocumentSummaryDto> summaries = new ArrayList<>();
            if (docs != null) {
                for (DocumentDto d : docs) {
                    DocumentSummaryDto s = new DocumentSummaryDto();
                    s.setTitle(d.getTitle());
                    s.setDescription(d.getDescription());
                    summaries.add(s);
                }
            }
            return summaries;

        } catch (Exception e) {
            System.out.println("Error fetching documents for user " + userId + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<NotificationSummaryDto> getNotificationSummaries(int userId) {
        try {
            List<NotificationDto> notifs = notificationRestClient.get()
                    .uri("/user/{userId}", userId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<NotificationDto>>() {});

            List<NotificationSummaryDto> summaries = new ArrayList<>();
            if (notifs != null) {
                for (NotificationDto n : notifs) {
                    NotificationSummaryDto s = new NotificationSummaryDto();
                    s.setMessage(n.getMessage());
                    s.setType(n.getType());
                    summaries.add(s);
                }
            }
            return summaries;
        } catch (Exception e) {
            System.out.println("Error fetching notifications for user " + userId + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // ------------------- Helpers: full objects for internal deletes -------------------

    private List<DocumentDto> getFullDocumentsByUserId(int userId) {
        try {
            return documentRestClient.get()
                    .uri("/user/{userId}", userId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<DocumentDto>>() {});
        } catch (Exception e) {
            System.out.println("Error fetching documents for user " + userId + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<NotificationDto> getFullNotificationsByUserId(int userId) {
        try {
            return notificationRestClient.get()
                    .uri("/user/{userId}", userId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<NotificationDto>>() {});
        } catch (Exception e) {
            System.out.println("Error fetching notifications for user " + userId + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // ------------------- Delete user with all linked data -------------------

    public String deleteUser(int id) {
        // delete notifications
        try {
            notificationRestClient.delete().uri("/user/{userId}", id).retrieve().body(Void.class);
        } catch (Exception e) {
            System.out.println("Error deleting notifications for user " + id + ": " + e.getMessage());
        }

        // delete documents
        List<DocumentDto> docs = getFullDocumentsByUserId(id);
        if (docs != null) {
            for (DocumentDto d : docs) {
                try {
                    documentRestClient.delete()
                            .uri("/{id}", d.getId())
                            .retrieve()
                            .body(Void.class);
                } catch (Exception e) {
                    System.out.println("Error deleting document id " + d.getId() + ": " + e.getMessage());
                }
            }
        }

        // delete the user
        userRepository.deleteUser(id);
        return "User deleted with ID: " + id;
    }

}
