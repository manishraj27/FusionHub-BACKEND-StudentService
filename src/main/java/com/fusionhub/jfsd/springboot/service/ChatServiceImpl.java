package com.fusionhub.jfsd.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.models.Chat;
import com.fusionhub.jfsd.springboot.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;
	
	@Override
	public Chat createChat(Chat chat) {
		return chatRepository.save(chat);
	}

}
