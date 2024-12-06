package com.fusionhub.jfsd.springboot.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);

		Map<String, Object> attributes = oauth2User.getAttributes();

		String email = (String) attributes.get("email");
		String name = (String) attributes.get("name");
		
		

		User user = userRepository.findByEmail(email);

		if (user == null) {
	        // Create a new user with status as "ACCEPTED" if not found
	        user = new User(email, name, "USER", "ACCEPTED", "Google");
	        user = userRepository.save(user);
	    } else if (!"ACCEPTED".equals(user.getStatus())) {
	        // If user exists and status is not ACCEPTED, throw an exception
	        throw new OAuth2AuthenticationException("Your account is " + user.getStatus());
	    }

		return new CustomOAuth2User(oauth2User, user);
	}
}
