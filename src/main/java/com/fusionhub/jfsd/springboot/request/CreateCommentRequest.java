package com.fusionhub.jfsd.springboot.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateCommentRequest {

	private Long issueId;
	private String content;
}
