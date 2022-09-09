package com.ninjaone.backendinterviewproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class RemoteMonitoringManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemoteMonitoringManagementApplication.class, args);
	}
}
