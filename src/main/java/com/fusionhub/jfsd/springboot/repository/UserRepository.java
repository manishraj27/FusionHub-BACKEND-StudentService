package com.fusionhub.jfsd.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionhub.jfsd.springboot.models.User;

public interface  UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	List<User> findAll(); 
}
