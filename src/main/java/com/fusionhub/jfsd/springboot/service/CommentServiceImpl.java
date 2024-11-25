package com.fusionhub.jfsd.springboot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.models.Comment;
import com.fusionhub.jfsd.springboot.models.Issue;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.repository.CommentRepository;
import com.fusionhub.jfsd.springboot.repository.IssueRepository;
import com.fusionhub.jfsd.springboot.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Comment createComment(Long issueId, Long userId, String content) throws Exception {
		Optional<Issue> issueOptional = issueRepository.findById(issueId);
		Optional<User> userOptional = userRepository.findById(userId);
		
		if(issueOptional.isEmpty()) {
			throw new Exception("issue not found with id "+ issueId);
		}
		if(userOptional.isEmpty()) {
			throw new Exception("user not found with id "+ userId);
		}
		Issue issue = issueOptional.get();
		User user = userOptional.get();
		
		Comment comment = new Comment();
		
		comment.setIssue(issue);
		comment.setUser(user);
		comment.setCreatedDateTime(LocalDateTime.now());
		comment.setContent(content);
		
		Comment savedComment = commentRepository.save(comment);
		
		issue.getComments().add(savedComment);
		
		return savedComment;
	}

	@Override
	public void deleteComment(Long commentId, Long userId) throws Exception {
		
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		Optional<User> userOptional = userRepository.findById(userId);
		
		if(commentOptional.isEmpty()) {
			throw new Exception("comment not found with id "+ commentId);
			
		}
		if(userOptional.isEmpty()){
			throw new Exception("user not found with id "+ userId);
		}
		
		Comment comment = commentOptional.get();
		User user = userOptional.get();
		
		if(comment.getUser().equals(user)) {
			commentRepository.delete(comment);
			
		}
		else {
			throw new Exception("user does not have permission to delete rhis comment");
		}
	}

	

	@Override
	public List<Comment> findCommetByIssueId(Long issueId) {
		return commentRepository.findByIssueId(issueId);
	}



}
