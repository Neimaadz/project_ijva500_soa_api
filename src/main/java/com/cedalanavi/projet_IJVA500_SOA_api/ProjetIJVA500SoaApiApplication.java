package com.cedalanavi.projet_IJVA500_SOA_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "IJVA500 Project API",
				version = "1.0",
				description = "IJVA500 Project Cecile Damien Laurie Nathan Victor")
		)
public class ProjetIJVA500SoaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetIJVA500SoaApiApplication.class, args);
	}
}
