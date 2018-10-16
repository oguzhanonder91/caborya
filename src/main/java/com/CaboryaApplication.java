package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.repository")
@EntityScan("com.example.entity")
@SpringBootApplication
public class CaboryaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaboryaApplication.class, args);
	}
}
