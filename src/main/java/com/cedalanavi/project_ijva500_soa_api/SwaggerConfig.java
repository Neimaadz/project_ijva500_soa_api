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
					+ "<h1>IJVA500 Project Cecile Damien Laurie Nathan Victor</h1>"
					+ "<h2>===> Read me <===</h2>"
					+ "<h4>${SWAGGER.TAG.CONDITION.REQUIRED} = Controller soumis à des conditions</h4>"
					+ "<h4>${SWAGGER.TAG.ACCESS.CONTROLED} = Endpoints du controller soumis à un contrôle de droit d'accès</h4>"
					+ "</html>")
		)
public class SwaggerConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/swagger-ui/index.html");
		registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html");
	}
	
	
}