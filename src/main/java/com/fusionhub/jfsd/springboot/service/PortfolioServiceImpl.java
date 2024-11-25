package com.fusionhub.jfsd.springboot.service;

import com.fusionhub.jfsd.springboot.DTO.PortfolioRequestDto;
import com.fusionhub.jfsd.springboot.DTO.ProjectDTO;
import com.fusionhub.jfsd.springboot.models.Portfolio;
import com.fusionhub.jfsd.springboot.models.Project;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.PortfolioRepository;
import com.fusionhub.jfsd.springboot.repository.ProjectRepository;
import com.fusionhub.jfsd.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Portfolio createOrUpdatePortfolio(User user, PortfolioRequestDto portfolioRequestDto) throws Exception {
        // Check if unique username already exists
        Portfolio existingPortfolioWithUsername = portfolioRepository.findByUniqueUsername(portfolioRequestDto.getUniqueUsername());
        Portfolio currentUserPortfolio = portfolioRepository.findByUser(user);

        // If another user already has this unique username
        if (existingPortfolioWithUsername != null && 
            (currentUserPortfolio == null || !existingPortfolioWithUsername.getId().equals(currentUserPortfolio.getId()))) {
            throw new Exception("Unique username already exists");
        }

        // Check if the user already has a portfolio
        Portfolio portfolio = currentUserPortfolio;
        if (portfolio == null) {
            portfolio = new Portfolio();
            portfolio.setUser(user);
        }

        // Update the portfolio with the provided details from the DTO
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

        // Save the portfolio to the database
        return portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio getPortfolioByUniqueUsername(String uniqueUsername) throws Exception {
        Portfolio portfolio = portfolioRepository.findByUniqueUsername(uniqueUsername);
        if (portfolio == null) {
            throw new Exception("Portfolio not found");
        }
        return portfolio;
    }

    @Override
    public Portfolio getPortfolioByUserId(Long userId) throws Exception {
        Portfolio portfolio = portfolioRepository.findByUserId(userId);
        if (portfolio == null) {
            throw new Exception("Portfolio not found");
        }
        return portfolio;
    }

    @Override
    public List<ProjectDTO> getPortfolioProjects(Portfolio portfolio) throws Exception {
        if (portfolio.getProjectIds() == null || portfolio.getProjectIds().isEmpty()) {
            return List.of();
        }

        List<Project> projects = projectRepository.findAllById(portfolio.getProjectIds());
        
        return projects.stream().map(project -> {
            ProjectDTO dto = new ProjectDTO();
            dto.setId(project.getId());
            dto.setName(project.getName());
            dto.setDescription(project.getDescription());
            dto.setCategory(project.getCategory());
            dto.setTags(project.getTags());
            return dto;
        }).collect(Collectors.toList());
    }
}