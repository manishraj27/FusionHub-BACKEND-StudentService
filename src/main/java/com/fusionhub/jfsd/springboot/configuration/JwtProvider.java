package com.fusionhub.jfsd.springboot.configuration;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

	static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
	
	public static String generateToken(Authentication auth) {
		
		String jwt = Jwts.builder().setIssuedAt(new Date())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+86400000))
				.claim("email", auth.getName())
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
