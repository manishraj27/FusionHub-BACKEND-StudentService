package com.fusionhub.jfsd.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

   
    @GetMapping("/")
    public String checkServerStatus() {
        return "Student Server is UP and Running!!";
    }
}