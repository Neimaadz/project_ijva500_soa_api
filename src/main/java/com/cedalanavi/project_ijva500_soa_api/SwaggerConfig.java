package com.cedalanavi.project_ijva500_soa_api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "IJVA500 Project API",
				version = "1.0",
				description = "IJVA500 Project Cecile Damien Laurie Nathan Victor")
		)
@SecurityScheme(
	    name = "bearerAuth",
	    description = "Put your JWT token (WITHOUT the prefix Bearer)",
		in = SecuritySchemeIn.HEADER,
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT",
	    scheme = "bearer"
	)
public class SwaggerConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/swagger-ui/index.html");
		registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html");
	}
	
	
}