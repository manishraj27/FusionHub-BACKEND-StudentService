package com.fusionhub.jfsd.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionhub.jfsd.springboot.models.Invitation;


public interface InvitationRepository extends JpaRepository<Invitation, Long>{
	
	Invitation findByToken(String token);
	
	Invitation findByEmail(String email);
}
