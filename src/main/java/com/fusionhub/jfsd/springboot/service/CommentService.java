package com.fusionhub.jfsd.springboot.service;

import java.util.List;

import com.fusionhub.jfsd.springboot.models.Comment;

public interface CommentService {
	
	Comment createComment(Long issueId, Long userId, String comment) throws Exception;
	
	void deleteComment(Long commentId, Long userId) throws Exception;
	
	List<Comment> findCommetByIssueId(Long issueId);
	
}
