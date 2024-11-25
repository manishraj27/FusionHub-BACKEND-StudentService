package com.fusionhub.jfsd.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Override
	public void sendEmailWithToken(String userEmail, String link) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		
		String subject = "FusionHub : Join Project Team Invitation";
		String text = "Click the link to join the Project Team: <br/>" + 
                "<a href='" + link + "'>" + link + "</a>";

		helper.setSubject(subject);
		helper.setText(text, true);
		helper.setTo(userEmail);
		helper.setFrom("samaavibes@gmail.com");
		
		try {
			javaMailSender.send(mimeMessage);
			
		} catch (MailSendException e) {
			e.printStackTrace();
			throw new MailSendException("Failed to send email");
		}
	}

}
