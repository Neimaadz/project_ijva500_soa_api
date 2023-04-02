package com.cedalanavi.project_ijva500_soa_api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "IJVA500 Project API",
				version = "1.0",
				description =
					"<html>"
					+ "<h2>IJVA500 Project Cecile Damien Laurie Nathan Victor</h2>"
					+ "<h3>===> Read me <===</h3>"
					+ "${SWAGGER.TAG.CONDITION.REQUIRED} = Controller soumis à des conditions"
					+ "<br>"
					+ "${SWAGGER.TAG.ACCESS.CONTROLED} = Endpoint du controller soumis à un contrôle d'accès"
					+ "</html>")
		)
public class SwaggerConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/swagger-ui/index.html");
		registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html");
	}
	
	
}