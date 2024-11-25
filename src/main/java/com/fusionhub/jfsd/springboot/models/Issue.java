package com.fusionhub.jfsd.springboot.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;



@Entity
@Data
public class Issue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	private String description;
	private String status;
	private Long projectID;
	private String priority;
	private LocalDate dueDate;
	private List<String> tags = new ArrayList<>();
	
	
	
	@ManyToOne
	private User assignee;

	@JsonIgnore
	@ManyToOne
	private Project project;
	
	@JsonIgnore //jsonignore dont fetch : to solve this recursion problem
	@OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true) //dont create seperate table for comment
	private List<Comment> comments = new ArrayList<>();
	
}
