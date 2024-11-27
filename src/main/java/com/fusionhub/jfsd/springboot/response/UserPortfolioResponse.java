package com.fusionhub.jfsd.springboot.response;

import java.util.List;

import com.fusionhub.jfsd.springboot.DTO.ProjectDTO;
import com.fusionhub.jfsd.springboot.models.Portfolio;
import com.fusionhub.jfsd.springboot.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPortfolioResponse {
    private User user;
    private Portfolio portfolio;
    private List<ProjectDTO> projectDTOs;
    private MessageResponse messageResponse;
}