package com.cedalanavi.project_ijva500_soa_api.User.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.project_ijva500_soa_api.User.Data.UserResource;
import com.cedalanavi.project_ijva500_soa_api.User.Data.UserUpdateRequest;

@Service
public class UserService {

	@Value("${user.service.url}")
	String userServiceUrl;

	@Value("${manage.user.rights.service.url}")
	String manageUserRightsServiceUrl;

	@Value("${authentication.service.url}")
	String authServiceUrl;
	
	@Autowired
    @Qualifier("myRestTemplate")
	RestTemplate restTemplate;


	public List<UserResource> getUsers() {
		return restTemplate.exchange(userServiceUrl + "/users", HttpMethod.GET,  null, new ParameterizedTypeReference<List<UserResource>>(){}).getBody();
	}
	
	public UserResource getUser(String idUser) {
		return restTemplate.getForEntity(userServiceUrl + "/user/" + idUser, UserResource.class).getBody();
	}
	
	public void updateUser(String idUser, UserUpdateRequest userUpdateRequest) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<UserUpdateRequest> request = new HttpEntity<UserUpdateRequest>(userUpdateRequest, headers);
		restTemplate.exchange(userServiceUrl + "/update/" + idUser, HttpMethod.PUT, request, Void.class);
	}

	public void deleteUser(String idUser) {
		restTemplate.delete(authServiceUrl + "/delete/" + idUser);
		restTemplate.delete(manageUserRightsServiceUrl + "/delete/" + idUser);
		restTemplate.delete(userServiceUrl + "/delete/" + idUser);
	}

}
