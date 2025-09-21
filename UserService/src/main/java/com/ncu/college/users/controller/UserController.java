// package com.ncu.college.users.controller;

// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/users")
// public class UserController {



//      @GetMapping("/hello")
//     public String hello() {
//         return "Hello from User Service running on port 8000!";
//     }
//     /**
//      * GET all users
//      * Example: http://localhost:8000/users/all
//      */
//     @GetMapping("/all")
//     public String getAllUsers() {
//         System.out.println("Fetching all users...");
//         return "Hello from User Service - List of users here";
//     }

//     /**
//      * GET a user by ID
//      * Example: http://localhost:8000/users/101
//      */
//     @GetMapping("/{id}")
//     public String getUserById(@PathVariable String id) {
//         System.out.println("Fetching user with ID: " + id);
//         return "User details for ID: " + id;
//     }

//     /**
//      * CREATE a new user
//      * Example: POST http://localhost:8000/users
//      */
//     @PostMapping
//     public String createUser(@RequestBody String user) {
//         System.out.println("Creating user: " + user);
//         return "User created successfully";
//     }

//     /**
//      * UPDATE user details
//      * Example: PUT http://localhost:8000/users/101
//      */
//     @PutMapping("/{id}")
//     public String updateUser(@PathVariable String id, @RequestBody String user) {
//         System.out.println("Updating user with ID: " + id + " Data: " + user);
//         return "User with ID " + id + " updated successfully";
//     }

//     /**
//      * DELETE a user
//      * Example: DELETE http://localhost:8000/users/101
//      */
//     @DeleteMapping("/{id}")
//     public String deleteUser(@PathVariable String id) {
//         System.out.println("Deleting user with ID: " + id);
//         return "User with ID " + id + " deleted successfully";
//     }
// }
package com.ncu.college.users.controller;

import com.ncu.college.users.dto.UserDto;
import com.ncu.college.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // =========================================
    // Simple health check
    // Example: GET http://localhost:8000/users/hello
    // =========================================
    @GetMapping("/hello")
    public String hello() {
        return "Hello from User Service running on port 8000!";
    }

    // =========================================
    // GET all users
    // Example: GET     
    // =========================================
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // =========================================
    // GET a user by ID
    // Example: GET http://localhost:8000/users/1
    // =========================================
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // =========================================
    // CREATE a new user
    // Example: POST http://localhost:8000/users
    // Request Body: { "name": "Alice", "email": "alice@example.com", "phone": "9999999999" }
    // =========================================
    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    // =========================================
    // UPDATE user details
    // Example: PUT http://localhost:8000/users/1
    // Request Body: { "name": "Alice Updated", "email": "alice2@example.com", "phone": "8888888888" }
    // =========================================
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    // =========================================
    // DELETE a user
    // Example: DELETE http://localhost:8000/users/1
    // =========================================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    // =========================================
    // SEARCH users by name
    // Example: GET http://localhost:8000/users/search?name=Alice
    // =========================================
    @GetMapping("/search")
    public List<UserDto> searchUsersByName(@RequestParam String name) {
        return userService.searchUsersByName(name);
    }

    // =========================================
    // UPDATE only password for a user
    // Example: PATCH http://localhost:8000/users/1/password?newPassword=secure123
    // =========================================
    @PatchMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable int id,
                                                 @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.updatePassword(id, newPassword));
    }
}
