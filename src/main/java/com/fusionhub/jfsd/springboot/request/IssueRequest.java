package com.fusionhub.jfsd.springboot.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class IssueRequest {

	private String title;
	private String description;
	private String status;
	private Long projectId;
	private String priority;
	private LocalDate dueDate;
}
