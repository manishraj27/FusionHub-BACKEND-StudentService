package com.fusionhub.jfsd.springboot.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fusionhub.jfsd.springboot.models.PasswordResetToken;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

	@Autowired
	private PasswordResetTokenRepository tokenRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void createPasswordResetTokenForUser(String email) throws Exception {
		// Verify user exists
		User user = userService.findUserByEmail(email);
		if (user == null) {
			throw new Exception("User not found with email: " + email);
		}

		// Delete any existing tokens for this user
		tokenRepository.deleteByUserEmail(email);

		// Create new token
		String token = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, email, LocalDateTime.now().plusHours(24));

		tokenRepository.save(passwordResetToken);

		// Send email with reset link
		String resetLink = "http://localhost:5173/reset-password?token=" + token;
		emailService.sendPasswordResetEmail(email, resetLink);
	}

	@Override
	@Transactional
	public void validateTokenAndResetPassword(String token, String newPassword) throws Exception {
		PasswordResetToken resetToken = tokenRepository.findByToken(token);

		if (resetToken == null) {
			throw new Exception("Invalid password reset token");
		}

		if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
			tokenRepository.delete(resetToken);
			throw new Exception("Password reset token has expired");
		}

		// Get user and update password
		User user = userService.findUserByEmail(resetToken.getUserEmail());
		user.setPassword(passwordEncoder.encode(newPassword));
		userService.saveUser(user);

		// Delete used token
		tokenRepository.delete(resetToken);
	}
}
