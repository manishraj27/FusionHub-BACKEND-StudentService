package com.fusionhub.jfsd.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.models.UserProfileUpdateDTO;
import com.fusionhub.jfsd.springboot.service.UserService;

@RestController
@RequestMapping("/api/self")
public class UserSelfController {

    @Autowired
    private UserService userService;
    
	 @GetMapping("/profile")
	    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
	    	
	    	User user = userService.findUserProfileByJwt(jwt);
	    	return new ResponseEntity<>(user, HttpStatus.OK);
	    }
	 
	  @PutMapping("/profile")
	    public ResponseEntity<User> updateUserProfile(@RequestHeader("Authorization") String jwt, @RequestBody UserProfileUpdateDTO updateDTO) throws Exception {
	        User user = userService.findUserProfileByJwt(jwt);
	        
	        // Update the profile fields
	        user.setUniversity(updateDTO.getUniversity());
	        user.setCompany(updateDTO.getCompany());
	        user.setAboutMe(updateDTO.getAboutMe());
	        user.setGithubLink(updateDTO.getGithubLink());
	        user.setLinkedinLink(updateDTO.getLinkedinLink());
	        user.setTwitterLink(updateDTO.getTwitterLink());
	        user.setSkills(updateDTO.getSkills());
	        
	        // Save updated user
	        User updatedUser = userService.saveUser(user);
	        
	        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	    }
	  
}
