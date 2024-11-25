package com.fusionhub.jfsd.springboot.service;

import com.fusionhub.jfsd.springboot.DTO.PortfolioRequestDto;
import com.fusionhub.jfsd.springboot.DTO.ProjectDTO;
import com.fusionhub.jfsd.springboot.models.Portfolio;
import com.fusionhub.jfsd.springboot.models.User;
import java.util.List;

public interface PortfolioService {
    Portfolio createOrUpdatePortfolio(User user, PortfolioRequestDto portfolioRequestDto) throws Exception;
    Portfolio getPortfolioByUniqueUsername(String uniqueUsername) throws Exception;
    Portfolio getPortfolioByUserId(Long userId) throws Exception;
    List<ProjectDTO> getPortfolioProjects(Portfolio portfolio) throws Exception;
}