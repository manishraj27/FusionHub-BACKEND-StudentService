package com.fusionhub.jfsd.springboot.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionhub.jfsd.springboot.DTO.ProjectDTO;

@Entity
@Data
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String about;
    private String theme = "default"; // Add theme attribute with a default value

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    
    @ElementCollection
    private List<String> skills;

    
    // Change this to store project IDs instead of project names
    @ElementCollection
    private List<Long> projectIds;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "portfolio_id") // This creates a foreign key in the Project table
    private List<Project> projects;

    
    @Transient // This annotation tells JPA not to persist this field in the database
    private List<ProjectDTO> projectDTOs;

    @ElementCollection
    private List<String> experiences;

    @ElementCollection
    private List<String> education;

    private String githubLink;
    private String linkedinLink;
    private String email;

    @Column(unique = true, nullable = false)
    private String uniqueUsername;
}