package com.fusionhub.jfsd.springboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ResponseEntity<?> createOrUpdatePortfolio(
            @RequestHeader("Authorization") String jwt,
            @RequestBody PortfolioRequestDto portfolioRequestDto
    ) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            Portfolio portfolio = portfolioService.createOrUpdatePortfolio(user, portfolioRequestDto);
            return new ResponseEntity<>(portfolio, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/public/{uniqueUsername}")
    public ResponseEntity<?> getPortfolioByUniqueUsername(
            @PathVariable String uniqueUsername
    ) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioByUniqueUsername(uniqueUsername);
            return new ResponseEntity<>(portfolio, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
}