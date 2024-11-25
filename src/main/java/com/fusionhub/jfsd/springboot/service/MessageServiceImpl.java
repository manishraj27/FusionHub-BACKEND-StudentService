package com.fusionhub.jfsd.springboot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.models.Chat;
import com.fusionhub.jfsd.springboot.models.Message;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.MessageRepository;
import com.fusionhub.jfsd.springboot.repository.UserRepository;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectService projectService;
	
	@Override
	public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
		User sender = userRepository.findById(senderId)
				.orElseThrow(() -> new Exception("user not found with id "+ senderId));
		
		Chat chat = projectService.getProjectById(projectId).getChat();
		
		Message message = new Message();
		message.setContent(content);
		message.setSender(sender);
		message.setCreatedAt(LocalDateTime.now());
		message.setChat(chat);
		Message savedMessage= messageRepository.save(message);
		
		chat.getMessages().add(savedMessage);
		
		return savedMessage;
	}

	@Override
	public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
		Chat chat = projectService.getChatByProjectId(projectId);
		List<Message> findByChatIdOrderByCreatedAtAsc = messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
		return findByChatIdOrderByCreatedAtAsc;
	}

}
