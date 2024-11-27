package com.fusionhub.jfsd.springboot.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.DTO.PortfolioRequestDto;
import com.fusionhub.jfsd.springboot.DTO.ProjectDTO;
import com.fusionhub.jfsd.springboot.models.Portfolio;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.service.PortfolioService;
import com.fusionhub.jfsd.springboot.service.UserService;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;

    // Create or update a portfolio
    @PostMapping
    public ResponseEntity<Portfolio> createOrUpdatePortfolio(
            @RequestHeader("Authorization") String jwt,
            @RequestBody PortfolioRequestDto portfolioRequestDto
    ) {
        try {
            // Fetch the user from the JWT token
            User user = userService.findUserProfileByJwt(jwt);

            // Create or update the portfolio associated with the user
            Portfolio portfolio = portfolioService.createOrUpdatePortfolio(user, portfolioRequestDto);

            return new ResponseEntity<>(portfolio, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exception and return an error message
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get portfolio by unique username (no JWT required here)
    @GetMapping("/public/{uniqueUsername}")
    public ResponseEntity<Portfolio> getPortfolioByUniqueUsername(
            @PathVariable String uniqueUsername
    ) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioByUniqueUsername(uniqueUsername);
            return new ResponseEntity<>(portfolio, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/public/portfolio-projects/{uniqueUsername}")
    public ResponseEntity<?> getPortfolioProjects(
            @PathVariable String uniqueUsername
    ) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioByUniqueUsername(uniqueUsername);
            List<ProjectDTO> projects = portfolioService.getPortfolioProjects(portfolio);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get portfolio by user ID (via JWT)
    @GetMapping("/user/{userId}")
    public ResponseEntity<Portfolio> getPortfolioByUserId(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            // Fetch user from the JWT token for authorization (to ensure the user is authenticated)
            userService.findUserProfileByJwt(jwt);

            // Get the portfolio by user ID
            Portfolio portfolio = portfolioService.getPortfolioByUserId(userId);
            return new ResponseEntity<>(portfolio, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
