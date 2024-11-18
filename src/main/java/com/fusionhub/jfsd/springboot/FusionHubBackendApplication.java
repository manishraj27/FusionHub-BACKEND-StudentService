package com.fusionhub.jfsd.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class FusionHubBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FusionHubBackendApplication.class, args);
		System.out.println("FusionHub Server is up and running!");
	}

}
