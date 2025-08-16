package com.ncu.college.users.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {



     @GetMapping("/hello")
    public String hello() {
        return "Hello from User Service running on port 8000!";
    }
    /**
     * GET all users
     * Example: http://localhost:8000/users/all
     */
    @GetMapping("/all")
    public String getAllUsers() {
        System.out.println("Fetching all users...");
        return "Hello from User Service - List of users here";
    }

    /**
     * GET a user by ID
     * Example: http://localhost:8000/users/101
     */
    @GetMapping("/{id}")
    public String getUserById(@PathVariable String id) {
        System.out.println("Fetching user with ID: " + id);
        return "User details for ID: " + id;
    }

    /**
     * CREATE a new user
     * Example: POST http://localhost:8000/users
     */
    @PostMapping
    public String createUser(@RequestBody String user) {
        System.out.println("Creating user: " + user);
        return "User created successfully";
    }

    /**
     * UPDATE user details
     * Example: PUT http://localhost:8000/users/101
     */
    @PutMapping("/{id}")
    public String updateUser(@PathVariable String id, @RequestBody String user) {
        System.out.println("Updating user with ID: " + id + " Data: " + user);
        return "User with ID " + id + " updated successfully";
    }

    /**
     * DELETE a user
     * Example: DELETE http://localhost:8000/users/101
     */
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        System.out.println("Deleting user with ID: " + id);
        return "User with ID " + id + " deleted successfully";
    }
}
