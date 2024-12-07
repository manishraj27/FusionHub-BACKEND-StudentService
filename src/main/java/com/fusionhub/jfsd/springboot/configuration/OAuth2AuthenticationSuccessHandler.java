package com.fusionhub.jfsd.springboot.configuration;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fusionhub.jfsd.springboot.service.CustomOAuth2User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        
        // Generate JWT token
        String token = JwtProvider.generateToken(authentication);
        
        // Redirect to frontend with token
        String redirectUrl = "https://fusionhub.netlify.app/oauth2/callback?token=" + token;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}