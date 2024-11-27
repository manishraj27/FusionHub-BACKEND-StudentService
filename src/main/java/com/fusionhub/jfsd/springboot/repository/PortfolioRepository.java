package com.fusionhub.jfsd.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fusionhub.jfsd.springboot.models.Portfolio;
import com.fusionhub.jfsd.springboot.models.User;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Portfolio findByUser(User user);
    Portfolio findByUniqueUsername(String uniqueUsername);
    Portfolio findByUserId(Long userId);
}