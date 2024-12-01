package com.fusionhub.jfsd.springboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.fusionhub.jfsd.springboot.models.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUserEmail(String userEmail);
    void deleteByUserEmail(String userEmail);
}