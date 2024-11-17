package com.fusionhub.jfsd.springboot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.configuration.JwtProvider;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User findUserProfileByJwt(String jwt) throws Exception {
		String email = JwtProvider.getEmailFromToken(jwt);
		
		return findUserByEmail(email);
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new Exception("USER NOT FOUND");
		}
		return user;
	}

	@Override
	public User findUserById(Long userId) throws Exception {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isEmpty()) {
			throw new Exception("User not found");
		}
		return optionalUser.get();
	}

	@Override
	public User updateUsersProjectSize(User user, int number) {
		user.setProjectSize(user.getProjectSize()+number);

		return userRepository.save(user);
	}

}
