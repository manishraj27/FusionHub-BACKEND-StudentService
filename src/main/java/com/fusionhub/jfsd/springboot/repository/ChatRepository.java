package com.fusionhub.jfsd.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionhub.jfsd.springboot.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
