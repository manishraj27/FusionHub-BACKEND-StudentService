package com.fusionhub.jfsd.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.models.Comment;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.request.CreateCommentRequest;
import com.fusionhub.jfsd.springboot.response.MessageResponse;
import com.fusionhub.jfsd.springboot.service.CommentService;
import com.fusionhub.jfsd.springboot.service.UserService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@PostMapping()
	public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserProfileByJwt(jwt);
		Comment createdComment = commentService.createComment(req.getIssueId(), user.getId(), req.getContent());
		return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserProfileByJwt(jwt);
		commentService.deleteComment(commentId, user.getId());
		MessageResponse res = new MessageResponse();
		res.setMessage("Comment deleted successfully");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/{issueId}")
	public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable Long issueId) {
		List<Comment> comments = commentService.findCommetByIssueId(issueId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

}
