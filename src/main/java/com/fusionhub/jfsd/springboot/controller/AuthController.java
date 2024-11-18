package com.fusionhub.jfsd.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.configuration.JwtProvider;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.UserRepository;
import com.fusionhub.jfsd.springboot.request.LoginRequest;
import com.fusionhub.jfsd.springboot.response.AuthResponse;
import com.fusionhub.jfsd.springboot.service.CustomUserDetailsImpl;
import com.fusionhub.jfsd.springboot.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private UserService userService;
    
	@Autowired
	private CustomUserDetailsImpl customUserDetailsImpl;
	
	 @PostMapping("/signup")
	    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
	        User isUserExist = userRepository.findByEmail(user.getEmail());
	        
	        if(isUserExist != null) {
	            throw new Exception("email already exist with another account");
	        }
	        
	        User createdUser = new User();
	        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
	        createdUser.setEmail(user.getEmail());
	        createdUser.setFullName(user.getFullName());
	        createdUser.setRole("USER"); // Set default role as USER
	        createdUser.setStatus("PENDING");
	        
	        User savedUser = userRepository.save(createdUser);
	        
	        // Create authentication with authorities
	        UserDetails userDetails = customUserDetailsImpl.loadUserByUsername(savedUser.getEmail());
	        Authentication authentication = new UsernamePasswordAuthenticationToken(
	            userDetails,
	            null,
	            userDetails.getAuthorities()
	        );
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        String jwt = JwtProvider.generateToken(authentication);
	        
	        AuthResponse res = new AuthResponse();
	        res.setMessage("signup success");
	        res.setJwt(jwt);
	        
	        return new ResponseEntity<>(res, HttpStatus.CREATED);
	    }
	    
	    @PostMapping("/signin")
	    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
	        String username = loginRequest.getEmail();
	        String password = loginRequest.getPassword();
	        
	        User user = userRepository.findByEmail(username);
	        if (user == null) {
	            throw new BadCredentialsException("Invalid username or password");
	        }
	        switch (user.getStatus().toUpperCase()) {
	        case "PENDING":
	            return new ResponseEntity<>(new AuthResponse(null, "Your account is pending approval"), HttpStatus.FORBIDDEN);
	        case "REJECTED":
	            return new ResponseEntity<>(new AuthResponse(null, "Your account has been rejected"), HttpStatus.FORBIDDEN);
	        case "ACCEPTED":
	            // Authenticate user
	            Authentication authentication = authenticate(username, password);
	            SecurityContextHolder.getContext().setAuthentication(authentication);

	            // Generate JWT
	            String jwt = JwtProvider.generateToken(authentication);
	            return new ResponseEntity<>(new AuthResponse(jwt, "signin success"), HttpStatus.OK);
	        default:
	            return new ResponseEntity<>(new AuthResponse(null, "Invalid account status"), HttpStatus.FORBIDDEN);
	    }
	        
	    }
	

    
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
    	
    	User user = userService.findUserProfileByJwt(jwt);
    	return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
	private Authentication authenticate(String username, String password)
	{
		UserDetails userDetails = customUserDetailsImpl.loadUserByUsername(username);
		if(userDetails == null) {
			throw new BadCredentialsException("invalid username");
			
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		
	}
	
}
