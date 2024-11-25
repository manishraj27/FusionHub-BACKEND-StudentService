package com.fusionhub.jfsd.springboot.service;



import com.fusionhub.jfsd.springboot.DTO.PortfolioRequestDto;
import com.fusionhub.jfsd.springboot.models.Portfolio;
import com.fusionhub.jfsd.springboot.models.User;

public interface PortfolioService {

    Portfolio createOrUpdatePortfolio(User user, PortfolioRequestDto portfolioRequestDto);

    Portfolio getPortfolioByUniqueUsername(String uniqueUsername);

    Portfolio getPortfolioByUserId(Long userId);
}
