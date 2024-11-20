package com.fusionhub.jfsd.springboot.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data //It is similar as generating getters and setters.
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fullName;
	private String email;
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
	private List<Issue> assignedIssues = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Project> projects = new ArrayList<>();  // added initialization

	@JsonIgnore
	private String role = "USER";
	
	private String status = "PENDING";
	 
	// New fields for additional details
    private String university;
    private String company;
    @Column(length = 1000) 
    private String aboutMe;
    private String githubLink;
    private String linkedinLink;
    private String twitterLink;
    @ElementCollection
    private List<String> skills = new ArrayList<>();
    
    
	private int projectSize; //for subscription use
}
