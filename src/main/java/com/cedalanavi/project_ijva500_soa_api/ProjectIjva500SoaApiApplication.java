package com.cedalanavi.project_ijva500_soa_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.cedalanavi.projet_IJVA500_SOA_utils.RestTemplateConfig;

@SpringBootApplication
@Import(RestTemplateConfig.class)
public class ProjectIjva500SoaApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectIjva500SoaApiApplication.class, args);
	}
}
