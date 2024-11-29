package com.fusionhub.jfsd.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.DTO.PortfolioRequestDto;
import com.fusionhub.jfsd.springboot.DTO.PortfolioUrlDTO;
import com.fusionhub.jfsd.springboot.DTO.ProjectDTO;
import com.fusionhub.jfsd.springboot.models.Portfolio;
import com.fusionhub.jfsd.springboot.models.Project;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.PortfolioRepository;
import com.fusionhub.jfsd.springboot.repository.ProjectRepository;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

//    @Autowired
//    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Override
    public Portfolio createOrUpdatePortfolio(User user, PortfolioRequestDto portfolioRequestDto) throws Exception {
        Portfolio existingPortfolioWithUsername = portfolioRepository.findByUniqueUsername(portfolioRequestDto.getUniqueUsername());
        Portfolio currentUserPortfolio = portfolioRepository.findByUser(user);

        if (existingPortfolioWithUsername != null && 
            (currentUserPortfolio == null || !existingPortfolioWithUsername.getId().equals(currentUserPortfolio.getId()))) {
            throw new Exception("Unique username already exists");
        }

        Portfolio portfolio = Optional.ofNullable(currentUserPortfolio).orElse(new Portfolio());
        portfolio.setUser(user);

        // Update basic fields
        portfolio.setName(portfolioRequestDto.getName());
        portfolio.setAbout(portfolioRequestDto.getAbout());
        portfolio.setTheme(portfolioRequestDto.getTheme() != null ? portfolioRequestDto.getTheme() : "default");
        portfolio.setSkills(portfolioRequestDto.getSkills());
        portfolio.setProjectIds(portfolioRequestDto.getProjectIds());
        portfolio.setExperiences(portfolioRequestDto.getExperiences());
        portfolio.setEducation(portfolioRequestDto.getEducation());
        portfolio.setGithubLink(portfolioRequestDto.getGithubLink());
        portfolio.setLinkedinLink(portfolioRequestDto.getLinkedinLink());
        portfolio.setEmail(portfolioRequestDto.getEmail());
        portfolio.setUniqueUsername(portfolioRequestDto.getUniqueUsername());

        // Save the portfolio first
        portfolio = portfolioRepository.save(portfolio);

        // Populate projects and projectDTOs
        if (portfolio.getProjectIds() != null && !portfolio.getProjectIds().isEmpty()) {
            List<Project> projects = new ArrayList<>();
            List<ProjectDTO> projectDTOs = new ArrayList<>();
            
            for (Long projectId : portfolio.getProjectIds()) {
                try {
                    Project project = projectService.getProjectById(projectId);
                    projects.add(project);
                    
                    ProjectDTO dto = new ProjectDTO(
                        project.getId(),
                        project.getName(),
                        project.getDescription(),
                        project.getCategory(),
                        project.getTags()
                    );
                    projectDTOs.add(dto);
                } catch (Exception e) {
                    // Log error but continue processing other projects
                    System.err.println("Error loading project ID " + projectId + ": " + e.getMessage());
                }
            }
            
            portfolio.setProjects(projects);
            portfolio.setProjectDTOs(projectDTOs);
        }

        return portfolio;
    }

    @Override
    public Portfolio getPortfolioByUniqueUsername(String uniqueUsername) throws Exception {
        Portfolio portfolio = portfolioRepository.findByUniqueUsername(uniqueUsername);
        if (portfolio == null) {
            throw new Exception("Portfolio not found");
        }

        // Populate projectDTOs
        if (portfolio.getProjectIds() != null && !portfolio.getProjectIds().isEmpty()) {
            List<ProjectDTO> projectDTOs = new ArrayList<>();
            
            for (Long projectId : portfolio.getProjectIds()) {
                try {
                    Project project = projectService.getProjectById(projectId);
                    ProjectDTO dto = new ProjectDTO(
                        project.getId(),
                        project.getName(),
                        project.getDescription(),
                        project.getCategory(),
                        project.getTags()
                    );
                    projectDTOs.add(dto);
                } catch (Exception e) {
                    // Log error but continue processing other projects
                    System.err.println("Error loading project ID " + projectId + ": " + e.getMessage());
                }
            }
            
            portfolio.setProjectDTOs(projectDTOs);
        }

        return portfolio;
    }

    @Override
    public List<ProjectDTO> getPortfolioProjects(Portfolio portfolio) throws Exception {
        if (portfolio == null) {
            throw new Exception("Portfolio not found");
        }

        if (portfolio.getProjectIds() == null || portfolio.getProjectIds().isEmpty()) {
            return new ArrayList<>();
        }

        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (Long projectId : portfolio.getProjectIds()) {
            try {
                Project project = projectService.getProjectById(projectId);
                ProjectDTO dto = new ProjectDTO(
                    project.getId(),
                    project.getName(),
                    project.getDescription(),
                    project.getCategory(),
                    project.getTags()
                );
                projectDTOs.add(dto);
            } catch (Exception e) {
                // Log error but continue processing other projects
                System.err.println("Error loading project ID " + projectId + ": " + e.getMessage());
            }
        }

        return projectDTOs;
    }

    @Override
    public Portfolio getPortfolioByUserId(Long userId) throws Exception {
        if (userId == null) {
            throw new Exception("User ID cannot be null");
        }

        Portfolio portfolio = portfolioRepository.findByUserId(userId);
        if (portfolio == null) {
            throw new Exception("Portfolio not found for user ID: " + userId);
        }

        // Populate projectDTOs similar to other methods
        if (portfolio.getProjectIds() != null && !portfolio.getProjectIds().isEmpty()) {
            List<ProjectDTO> projectDTOs = new ArrayList<>();
            
            for (Long projectId : portfolio.getProjectIds()) {
                try {
                    Project project = projectService.getProjectById(projectId);
                    ProjectDTO dto = new ProjectDTO(
                        project.getId(),
                        project.getName(),
                        project.getDescription(),
                        project.getCategory(),
                        project.getTags()
                    );
                    projectDTOs.add(dto);
                } catch (Exception e) {
                    // Log error but continue processing other projects
                    System.err.println("Error loading project ID " + projectId + ": " + e.getMessage());
                }
            }
            
            portfolio.setProjectDTOs(projectDTOs);
        }

        return portfolio;
    }
    
   

    
    @Override
    public List<Portfolio> getAllPortfolios() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        
        // Populate projectDTOs for each portfolio
        for (Portfolio portfolio : portfolios) {
            if (portfolio.getProjectIds() != null && !portfolio.getProjectIds().isEmpty()) {
                List<ProjectDTO> projectDTOs = new ArrayList<>();
                
                for (Long projectId : portfolio.getProjectIds()) {
                    try {
                        Project project = projectService.getProjectById(projectId);
                        ProjectDTO dto = new ProjectDTO(
                            project.getId(),
                            project.getName(),
                            project.getDescription(),
                            project.getCategory(),
                            project.getTags()
                        );
                        projectDTOs.add(dto);
                    } catch (Exception e) {
                        System.err.println("Error loading project ID " + projectId + ": " + e.getMessage());
                    }
                }
                
                portfolio.setProjectDTOs(projectDTOs);
            }
        }
        
        return portfolios;
    }
    
    
    @Override
    public List<PortfolioUrlDTO> getAllPortfolioUrls() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        List<PortfolioUrlDTO> portfolioUrls = new ArrayList<>();
        
        String baseUrl = "http://localhost:5173/share/"; // public frontend url
        
        for (Portfolio portfolio : portfolios) {
            User user = portfolio.getUser();
            PortfolioUrlDTO urlDTO = new PortfolioUrlDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                portfolio.getUniqueUsername(),
                baseUrl + portfolio.getUniqueUsername()
            );
            portfolioUrls.add(urlDTO);
        }
        
        return portfolioUrls;
    }
}