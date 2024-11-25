package com.fusionhub.jfsd.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionhub.jfsd.springboot.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByIssueId(Long issueId);
}
