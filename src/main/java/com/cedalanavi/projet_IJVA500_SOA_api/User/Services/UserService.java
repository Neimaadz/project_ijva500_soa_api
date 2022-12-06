package com.cedalanavi.projet_IJVA500_SOA_api.User.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.projet_IJVA500_SOA_api.User.Data.UserUpdateRequest;

@Service
public class UserService {

	@Value("${user.service.url}")
	String userServiceUrl;
	
	@Autowired
    @Qualifier("myRestTemplate")
	RestTemplate restTemplate;
	
	public void updateUser(@RequestBody UserUpdateRequest userRequest, @PathVariable int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserUpdateRequest> request = new HttpEntity<UserUpdateRequest>(userRequest, headers);
		restTemplate.exchange(userServiceUrl + "/update/" + id, HttpMethod.PUT, request, Void.class);
	}

	public void deleteUser(@PathVariable int id) {
		restTemplate.delete(userServiceUrl + "/delete/" + id);
	}

}
