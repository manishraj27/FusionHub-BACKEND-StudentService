package com.fusionhub.jfsd.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionhub.jfsd.springboot.models.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>{
	
	public List<Issue> findByProjectId(Long id);

}
