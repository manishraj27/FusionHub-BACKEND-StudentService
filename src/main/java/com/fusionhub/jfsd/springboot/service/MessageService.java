package com.fusionhub.jfsd.springboot.service;

import java.util.List;

import com.fusionhub.jfsd.springboot.models.Message;

public interface MessageService {

	Message sendMessage(Long senderId, Long chatId, String content) throws Exception;
	
	List<Message> getMessagesByProjectId(Long projectId) throws Exception;
	
}
