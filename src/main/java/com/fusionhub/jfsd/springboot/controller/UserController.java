package com.fusionhub.jfsd.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.UserRepository;
import com.fusionhub.jfsd.springboot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    
    // Get all users
    @GetMapping("/viewall")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
    	User user = userService.findUserProfileByJwt(jwt);
    	return new ResponseEntity<>(user, HttpStatus.OK);
    	
    }
}
