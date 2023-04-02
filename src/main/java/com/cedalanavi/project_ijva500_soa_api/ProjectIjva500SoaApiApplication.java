package com.cedalanavi.project_ijva500_soa_api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
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

	@Bean(name="myRestTemplate")
	RestTemplate collectCentRestTemplate(RestTemplateBuilder builder, HttpServletRequest httpServletRequest) {
	    return builder.rootUri("/**")
	            .additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> {
	            	Object sessionAuthStored = httpServletRequest.getSession().getAttribute("Authorization");
	            	if (sessionAuthStored != null) {
		                request.getHeaders().add("Authorization", sessionAuthStored.toString());
	            	}
	                return execution.execute(request, body);
	            }).build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectIjva500SoaApiApplication.class, args);
	}
}
