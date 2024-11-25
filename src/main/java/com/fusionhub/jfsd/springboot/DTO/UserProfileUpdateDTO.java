package com.fusionhub.jfsd.springboot.DTO;

import java.util.List;

import lombok.Data;

@Data
public class UserProfileUpdateDTO {
	    private String university;
	    private String company;
	    private String aboutMe;
	    private String githubLink;
	    private String linkedinLink;
	    private String twitterLink;
	    private List<String> skills;
}
