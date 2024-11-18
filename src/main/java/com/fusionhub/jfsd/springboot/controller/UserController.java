package com.fusionhub.jfsd.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.UserRepository;
import com.fusionhub.jfsd.springboot.service.UserService;

@RestController
@RequestMapping("/adminapi/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Endpoint to get all users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // Ensures only ADMINs can access this endpoint
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Endpoint to get a user by ID
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')") // Ensures only ADMINs can access this endpoint
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User requestedUser = userRepository.findById(userId).orElse(null);
        if (requestedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(requestedUser);
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')") // Ensures only ADMINs can access this endpoint
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}
