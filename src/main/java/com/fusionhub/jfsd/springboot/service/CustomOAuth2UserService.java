package com.fusionhub.jfsd.springboot.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
		
		String provider = userRequest.getClientRegistration().getRegistrationId();

		if (provider.equals("github")) {
			email = (String) attributes.get("email");
			name = (String) attributes.get("login"); // GitHub uses "login" for username

			// If email is null (GitHub may not provide email)
			if (email == null) {
				// You might want to handle this case differently
				email = name + "@github.com";
			}
		} else {
			// Google case
			email = (String) attributes.get("email");
			name = (String) attributes.get("name");
		}

		User user = userRepository.findByEmail(email);

		if (user == null) {
			user = new User(email, name, "USER", "ACCEPTED"); // Create a new user if not found
			user = userRepository.save(user); // Save the new user
		}

		return new CustomOAuth2User(oauth2User, user);
	}
}
