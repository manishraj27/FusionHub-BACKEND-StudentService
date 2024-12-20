package com.fusionhub.jfsd.springboot.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.models.Invitation;
import com.fusionhub.jfsd.springboot.repository.InvitationRepository;

import jakarta.mail.MessagingException;

@Service
public class InvitationServiceImpl implements InvitationService {
	
	@Autowired
	private InvitationRepository invitationRepository;

	@Autowired
	private EmailService emailService;
	
	@Override
	public void sendInvitation(String email, Long projectId) throws MessagingException {
		String invitationToken = UUID.randomUUID().toString();
		
		Invitation invitation = new Invitation();
		invitation.setEmail(email);
		invitation.setProjectId(projectId);
		invitation.setToken(invitationToken);
		
		invitationRepository.save(invitation);
		
		String invitationLink = "https://fusionhub.netlify.app/accept_invitation?token="+invitationToken;
		emailService.sendEmailWithToken(email, invitationLink);
	}

	@Override
	public Invitation acceptInvitation(String token, Long userId) throws Exception {
		Invitation invitation = invitationRepository.findByToken(token);
		if(invitation == null) {
			throw new Exception("invalid invitation token");
		}
		return invitation;
		
	}

	@Override
	public String getTokenByUserMail(String userEmail) {
		
		Invitation invitation = invitationRepository.findByEmail(userEmail);
		
		return invitation.getToken();
	}

	@Override
	public void deleteToken(String token) {
		
		Invitation invitation = invitationRepository.findByToken(token);
		
		invitationRepository.delete(invitation);
		
	}

}
