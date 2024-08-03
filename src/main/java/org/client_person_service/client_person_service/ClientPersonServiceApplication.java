package org.client_person_service.client_person_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class ClientPersonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientPersonServiceApplication.class, args);
	}


}
