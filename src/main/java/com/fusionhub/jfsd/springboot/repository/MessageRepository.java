package com.fusionhub.jfsd.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionhub.jfsd.springboot.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
	
	List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
