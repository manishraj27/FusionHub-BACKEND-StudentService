package com.fusionhub.jfsd.springboot.configuration;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import com.fusionhub.jfsd.springboot.service.CustomOAuth2User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

	static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
	
	public static String generateToken(Authentication auth) {
	    Object principal = auth.getPrincipal();
	    String email;
	    
	    if (principal instanceof CustomOAuth2User) {
	        email = ((CustomOAuth2User) principal).getEmail(); // Get email from OAuth2 user
	    } else {
	        // Standard user authentication (e.g., normal user registration/login)
	        email = auth.getName(); // Default to the authentication name
	    }

	    String jwt = Jwts.builder()
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(new Date().getTime() + 86400000))
	            .claim("email", email) 
	            .claim("role", "USER")
	            .claim("authorities", auth.getAuthorities())
	            .signWith(key)
	            .compact();

	    return jwt;
	}

	
	
	 public static String getEmailFromToken(String jwt) {
	       
		 jwt = jwt.substring(7);
		 Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
	        
	       String email = String.valueOf(claims.get("email"));
	       return email;
	    }
	 
	 public static String getRoleFromToken(String jwt) {
	        jwt = jwt.substring(7);
	        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
	        return String.valueOf(claims.get("role"));
	    }
	 
}
