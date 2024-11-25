package com.fusionhub.jfsd.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.models.Issue;
import com.fusionhub.jfsd.springboot.models.IssueDTO;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.request.IssueRequest;
import com.fusionhub.jfsd.springboot.response.MessageResponse;
import com.fusionhub.jfsd.springboot.service.IssueService;
import com.fusionhub.jfsd.springboot.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

	@Autowired
	private IssueService issueService;

	@Autowired
	private UserService userService;

	@GetMapping("/{issueId}")
	public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {
		return ResponseEntity.ok(issueService.getIssueById(issueId));

	}

	@GetMapping("/project/{projectId}")
	public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception {
		return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
	}

	@PostMapping
	public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue,
			@RequestHeader("Authorization") String token) throws Exception {

		System.out.println("issue ----" + issue);

		User tokenUser = userService.findUserProfileByJwt(token);
		User user = userService.findUserById(tokenUser.getId());

		Issue createdIssue = issueService.createIssue(issue, tokenUser);
		IssueDTO issueDTO = new IssueDTO();

		issueDTO.setDescription(createdIssue.getDescription());
		issueDTO.setDueDate(createdIssue.getDueDate());
		issueDTO.setId(createdIssue.getId());
		issueDTO.setPriority(createdIssue.getPriority());
		issueDTO.setProject(createdIssue.getProject());
		issueDTO.setProjectID(createdIssue.getProjectID());
		issueDTO.setStatus(createdIssue.getStatus());
		issueDTO.setTags(createdIssue.getTags());
		issueDTO.setTitle(createdIssue.getTitle());
		issueDTO.setAssignee(createdIssue.getAssignee());

		return ResponseEntity.ok(issueDTO);

	}

	@DeleteMapping("/{issueId}")
	public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
			@RequestHeader("Authorization") String token) throws Exception {
		User user = userService.findUserProfileByJwt(token);
		issueService.deleteIssue(issueId, user.getId());

		MessageResponse res = new MessageResponse();
		res.setMessage("Issue deleted");

		return ResponseEntity.ok(res);
	}

	@PutMapping("/{issueId}/assignee/{userId}")
	public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,
			@PathVariable Long userId)
			throws Exception {

		Issue issue = issueService.addUserToIssue(issueId, userId);

		return ResponseEntity.ok(issue);
	}
	
	@PutMapping("/{issueId}/status/{status}")
	public ResponseEntity<Issue> updateIssueStatus(
			@PathVariable String status,
			@PathVariable Long issueId) throws Exception{
		Issue issue = issueService.updateStatus(issueId, status);
		
		return ResponseEntity.ok(issue);
	}
}
