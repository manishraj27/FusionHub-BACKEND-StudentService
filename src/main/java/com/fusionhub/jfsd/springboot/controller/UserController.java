package com.fusionhub.jfsd.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.UserRepository;
import com.fusionhub.jfsd.springboot.service.UserService;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) throws Exception {
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}