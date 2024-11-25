package com.fusionhub.jfsd.springboot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private List<String> tags;
}