package com.cedalanavi.project_ijva500_soa_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)	// enable annotation e.g: @preAuthorize()...
public class ProjectIjva500SoaApiApplication {
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.cors().and().csrf().disable();
			//.authorizeRequests().antMatchers("/**").permitAll();
        return http.build();
    }
    
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectIjva500SoaApiApplication.class, args);
	}
}
