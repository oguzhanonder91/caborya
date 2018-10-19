package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.request.RequestContextListener;

@EnableJpaRepositories
@SpringBootApplication
public class CaboryaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaboryaApplication.class, args);
	}

}
