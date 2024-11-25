package com.fusionhub.jfsd.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.models.Chat;
import com.fusionhub.jfsd.springboot.models.Message;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.request.CreateMessageRequest;
import com.fusionhub.jfsd.springboot.service.MessageService;
import com.fusionhub.jfsd.springboot.service.ProjectService;
import com.fusionhub.jfsd.springboot.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@PostMapping("/send")
	public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception {

		User user = userService.findUserById(request.getSenderId());

		Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
		if (chats == null) {
			throw new Exception("Chats not found");
		}

		Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(),
				request.getContent());

		return ResponseEntity.ok(sentMessage);
	}

	@GetMapping("/chat/{projectId}")
	public ResponseEntity<List<Message>> getMessageByChatId(@PathVariable Long projectId) throws Exception {
		List<Message> messages = messageService.getMessagesByProjectId(projectId);
		return ResponseEntity.ok(messages);
	}

}
