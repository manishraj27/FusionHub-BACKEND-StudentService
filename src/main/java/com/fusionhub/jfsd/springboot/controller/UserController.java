package com.fusionhub.jfsd.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // i think this annotation is only for 
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User requestedUser = userRepository.findById(userId).orElse(null);
        if (requestedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(requestedUser);
    }


    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }
    
    
    @PutMapping("/{userId}/status")  
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUserStatus(
        @PathVariable Long userId,
        @RequestParam String status
    ) {
        try {
            if (!status.equalsIgnoreCase("ACCEPTED") && 
                !status.equalsIgnoreCase("REJECTED") && 
                !status.equalsIgnoreCase("PENDING")) {
                return ResponseEntity.badRequest()
                    .body("Invalid status. Use 'ACCEPTED', 'REJECTED', or 'PENDING'.");
            }

            User user = userService.findUserById(userId);
            user.setStatus(status.toUpperCase());
            userRepository.save(user);
            
            return ResponseEntity.ok("User status updated to " + status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating user status");
        }
    }

}
