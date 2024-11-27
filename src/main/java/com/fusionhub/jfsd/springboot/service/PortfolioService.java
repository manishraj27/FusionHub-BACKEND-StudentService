package com.fusionhub.jfsd.springboot.service;

import java.util.List;

import com.fusionhub.jfsd.springboot.DTO.PortfolioRequestDto;
import com.fusionhub.jfsd.springboot.DTO.PortfolioUrlDTO;
import com.fusionhub.jfsd.springboot.DTO.ProjectDTO;
import com.fusionhub.jfsd.springboot.models.Portfolio;
import com.fusionhub.jfsd.springboot.models.User;

public interface PortfolioService {
	List<Portfolio> getAllPortfolios();
    Portfolio createOrUpdatePortfolio(User user, PortfolioRequestDto portfolioRequestDto) throws Exception;
    Portfolio getPortfolioByUniqueUsername(String uniqueUsername) throws Exception;
    Portfolio getPortfolioByUserId(Long userId) throws Exception;
    List<ProjectDTO> getPortfolioProjects(Portfolio portfolio) throws Exception;
    List<PortfolioUrlDTO> getAllPortfolioUrls();
}