package com.cedalanavi.project_ijva500_soa_api.User.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.project_ijva500_soa_api.User.Data.UserResource;
import com.cedalanavi.project_ijva500_soa_api.User.Data.UserUpdateRequest;

@Service
public class UserService {

	@Value("${user.service.url}")
	String userServiceUrl;
	
	@Autowired
    @Qualifier("myRestTemplate")
	RestTemplate restTemplate;


	public List<UserResource> getUsers() {
		return restTemplate.exchange(userServiceUrl + "/users", HttpMethod.GET,  null, new ParameterizedTypeReference<List<UserResource>>(){}).getBody();
	}
	
	public UserResource getUser(int id) {
		return restTemplate.getForEntity(userServiceUrl + "/user/" + id, UserResource.class).getBody();
	}
	
	public void updateUser(UserUpdateRequest userRequest, int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserUpdateRequest> request = new HttpEntity<UserUpdateRequest>(userRequest, headers);
		restTemplate.exchange(userServiceUrl + "/update/" + id, HttpMethod.PUT, request, Void.class);
	}

	public void deleteUser(int id) {
		restTemplate.delete(userServiceUrl + "/delete/" + id);
	}

}
