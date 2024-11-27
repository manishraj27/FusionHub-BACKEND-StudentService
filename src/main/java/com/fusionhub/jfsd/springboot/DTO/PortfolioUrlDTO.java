package com.fusionhub.jfsd.springboot.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioUrlDTO {
 private Long userId;
 private String userName;
 private String uniqueUsername;
 private String portfolioUrl;
}