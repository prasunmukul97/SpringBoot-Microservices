package com.learn.springSecurity.springsecurityJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringsecurityJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityJpaApplication.class, args);
	}

}
