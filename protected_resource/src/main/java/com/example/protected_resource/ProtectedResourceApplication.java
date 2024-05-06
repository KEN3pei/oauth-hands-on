package com.example.protected_resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProtectedResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProtectedResourceApplication.class, args);
	}

}
