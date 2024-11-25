package com.fusionhub.jfsd.springboot.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String about;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    
    @ElementCollection
    private List<String> skills;

    @ElementCollection
    private List<String> projects;

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
