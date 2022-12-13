package com.cedalanavi.project_ijva500_soa_api.Authentication.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.AuthCredentialsUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.AuthenticationRequest;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.AuthenticationResource;
import com.cedalanavi.project_ijva500_soa_api.User.Data.UserCreateRequest;

@Service
public class AuthenticationService {

	@Value("${authentication.service.url}")
	String authServiceUrl;
	
	@Value("${user.service.url}")
	String userServiceUrl;

	@Autowired
    @Qualifier("myRestTemplate")
	RestTemplate restTemplate;

	public void register(@RequestBody AuthenticationRequest authenticationRequest) {
		HttpEntity<AuthenticationRequest> authCreateRequest = new HttpEntity<AuthenticationRequest>(authenticationRequest);
		UserCreateRequest response = restTemplate.exchange(authServiceUrl + "/register", HttpMethod.POST, authCreateRequest, UserCreateRequest.class).getBody();
		
		if (response != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + response.token);
			HttpEntity<UserCreateRequest> userCreateRequest = new HttpEntity<UserCreateRequest>(response, headers);
			restTemplate.exchange(userServiceUrl + "/create", HttpMethod.POST, userCreateRequest, Void.class);
		}
	}
	
	public AuthenticationResource signin(AuthenticationRequest authenticationRequest) {
		HttpEntity<AuthenticationRequest> request = new HttpEntity<AuthenticationRequest>(authenticationRequest);
		return restTemplate.exchange(authServiceUrl + "/signin", HttpMethod.POST, request, AuthenticationResource.class).getBody();
	}
	
	public void updateUserCredentials(AuthCredentialsUpdateRequest authCredentialsUpdateRequest) {
		HttpEntity<AuthCredentialsUpdateRequest> request = new HttpEntity<AuthCredentialsUpdateRequest>(authCredentialsUpdateRequest);
		restTemplate.exchange(authServiceUrl + "/updateCredentials", HttpMethod.PUT, request, void.class);
	}

}
