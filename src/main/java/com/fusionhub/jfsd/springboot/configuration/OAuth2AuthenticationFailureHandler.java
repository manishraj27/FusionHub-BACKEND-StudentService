package com.fusionhub.jfsd.springboot.configuration;

import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        String errorMessage = exception != null && exception.getMessage() != null 
            ? exception.getMessage() 
            : "Authentication failed. Please try again.";
            
        // URL encode the error message to handle special characters
        String encodedError = java.net.URLEncoder.encode(errorMessage, "UTF-8");
        
        // Redirect to frontend with error message
        String redirectUrl = "http://localhost:5173/oauth2/callback?error=" + encodedError;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}