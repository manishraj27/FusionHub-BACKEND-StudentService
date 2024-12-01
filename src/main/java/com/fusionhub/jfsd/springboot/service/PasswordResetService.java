package com.fusionhub.jfsd.springboot.service;

public interface PasswordResetService {
    
    void createPasswordResetTokenForUser(String email) throws Exception;
  
    void validateTokenAndResetPassword(String token, String newPassword) throws Exception;
}