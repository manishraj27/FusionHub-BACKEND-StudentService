package com.fusionhub.jfsd.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionhub.jfsd.springboot.models.User;

public interface  UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
}
