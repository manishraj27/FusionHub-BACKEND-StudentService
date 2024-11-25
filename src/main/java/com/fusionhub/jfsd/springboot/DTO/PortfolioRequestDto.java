package com.fusionhub.jfsd.springboot.DTO;


import java.util.List;

import lombok.Data;

@Data
public class PortfolioRequestDto {
    private String name;
    private String about;
    private List<String> skills;
    private List<String> projects;
    private List<String> experiences;
    private List<String> education;
    private String githubLink;
    private String linkedinLink;
    private String email;
    private String uniqueUsername;
}
