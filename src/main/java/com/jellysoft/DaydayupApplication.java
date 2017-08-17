package com.jellysoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories( basePackages = "com.jellysoft.repository" )
@EntityScan( basePackages = "com.jellysoft" )
public class DaydayupApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaydayupApplication.class, args);
	} aa
}