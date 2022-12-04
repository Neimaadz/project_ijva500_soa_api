package com.cedalanavi.projet_IJVA500_SOA_api.UserService.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.projet_IJVA500_SOA_api.UserService.Data.CreateUserRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.UserService.Data.UpdateUserRequest;

@Service
public class UserService {

	@Value("${user.service.url}")
	String userServiceUrl;

	RestTemplate restTemplate = new RestTemplate();

	public void createUser(@RequestBody CreateUserRequest userRequest) {
		HttpEntity<CreateUserRequest> request = new HttpEntity<CreateUserRequest>(userRequest);
		restTemplate.exchange(userServiceUrl + "/create", HttpMethod.POST, request, Void.class);
	}
	
	public void updateUser(@RequestBody UpdateUserRequest userRequest, @PathVariable int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UpdateUserRequest> request = new HttpEntity<UpdateUserRequest>(userRequest, headers);
		restTemplate.exchange(userServiceUrl + "/update/" + id, HttpMethod.PUT, request, Void.class);
	}

	public void deleteUser(@PathVariable int id) {
		restTemplate.delete(userServiceUrl + "/delete/" + id);
	}

}
