package com.fusionhub.jfsd.springboot.service;

import com.fusionhub.jfsd.springboot.models.User;

public interface UserService 
{
	User findUserProfileByJwt(String jwt) throws Exception;
	
	User findUserByEmail(String email) throws Exception;
	
	User findUserById(Long userId) throws Exception;
	
	User updateUsersProjectSize(User user, int number);
	
	
}
