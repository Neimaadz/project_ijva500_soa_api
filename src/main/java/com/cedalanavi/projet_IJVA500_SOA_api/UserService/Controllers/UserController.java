package com.cedalanavi.projet_IJVA500_SOA_api.UserService.Controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.projet_IJVA500_SOA_api.UserService.CreateUserRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.UserService.UpdateUserRequest;

@RestController
@RequestMapping("/service-user")
public class UserController {

	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${user.service.url}")
	String userServiceUrl;

	@PostMapping("/create")
	public void createUser(@RequestBody CreateUserRequest userRequest, HttpServletResponse response) {
		HttpEntity<CreateUserRequest> request = new HttpEntity<CreateUserRequest>(userRequest);
		restTemplate.exchange(userServiceUrl + "/create", HttpMethod.POST, request, Void.class);
	}
	
	@PutMapping("/update/{id}")
	public void updateUser(@RequestBody UpdateUserRequest userRequest, @PathVariable int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UpdateUserRequest> request = new HttpEntity<UpdateUserRequest>(userRequest, headers);
		restTemplate.exchange(userServiceUrl + "/update/" + id, HttpMethod.PUT, request, Void.class);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable int id) {
		restTemplate.delete(userServiceUrl + "/delete/" + id);
	}

}
