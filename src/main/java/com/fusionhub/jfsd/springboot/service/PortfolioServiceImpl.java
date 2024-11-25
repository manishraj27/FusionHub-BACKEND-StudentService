package com.fusionhub.jfsd.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.DTO.PortfolioRequestDto;
import com.fusionhub.jfsd.springboot.models.Portfolio;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.PortfolioRepository;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public Portfolio createOrUpdatePortfolio(User user, PortfolioRequestDto portfolioRequestDto) {
        // Check if the user already has a portfolio
        Portfolio portfolio = portfolioRepository.findByUser(user);
        if (portfolio == null) {
            // If no portfolio exists, create a new one
            portfolio = new Portfolio();
            portfolio.setUser(user);
        }

        // Update the portfolio with the provided details from the DTO
        portfolio.setName(portfolioRequestDto.getName());
        portfolio.setAbout(portfolioRequestDto.getAbout());
        portfolio.setSkills(portfolioRequestDto.getSkills());
        portfolio.setProjects(portfolioRequestDto.getProjects());
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
    public Portfolio getPortfolioByUniqueUsername(String uniqueUsername) {
        return portfolioRepository.findByUniqueUsername(uniqueUsername);
    }

    @Override
    public Portfolio getPortfolioByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }
}
