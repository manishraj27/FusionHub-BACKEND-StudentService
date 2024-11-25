package com.fusionhub.jfsd.springboot.service;

import java.util.List;
import java.util.Optional;

import com.fusionhub.jfsd.springboot.models.Issue;
import com.fusionhub.jfsd.springboot.models.User;
import com.fusionhub.jfsd.springboot.request.IssueRequest;

public interface IssueService {
	
	Issue getIssueById(Long issueId) throws Exception;
	
	List<Issue> getIssueByProjectId(Long projectId) throws Exception;
	
	Issue createIssue(IssueRequest issue, User user) throws Exception;
	
	//Optional<Issue> updateIssue(Long issueid, IssueRequest updatedIssue, Long userid) throws Exception;
	
	void deleteIssue(Long issueId, Long userid) throws Exception;
	
//	List<Issue> getIssuesByAssigneeId(Long assigneeId) throws Exception;
	
//	List<Issue> searchIssues(String title, String status, String priority, Long assigneeId) throws Exception;
	
//	List<User> getAssigneeForIssue(Long issueId) throws Exception;
	
	Issue addUserToIssue(Long issueId, Long userId) throws Exception;
	
	Issue updateStatus(Long issueId, String status) throws Exception;
	
}
