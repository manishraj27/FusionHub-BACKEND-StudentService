package com.fusionhub.jfsd.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.UserRepository;

@Service
public class CustomUserDetailsImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("user not found with email "+username);
		}
		
		//since now it is not role based we'll grant empty granted authority
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		return new org.springframework.security.core.userdetails.User(user.getEmail() ,user.getPassword(), 
				authorities);
	}

	
}