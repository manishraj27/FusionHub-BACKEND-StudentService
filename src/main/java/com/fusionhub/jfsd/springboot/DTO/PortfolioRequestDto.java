package com.fusionhub.jfsd.springboot.DTO;

import lombok.Data;
import java.util.List;

@Data
public class PortfolioRequestDto {
    private String name;
    private String about;
    private String theme; // Add theme
    private List<String> skills;
    private List<Long> projectIds; // Change to project IDs
    private List<String> experiences;
    private List<String> education;
    private String githubLink;
    private String linkedinLink;
    private String email;
    private String uniqueUsername;
}