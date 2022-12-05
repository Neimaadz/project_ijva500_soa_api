package com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Data.AuthenticationRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Data.AuthenticationResource;
import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Data.UserCredentialsUpdateRequest;

@Service
public class AuthenticationService {

	@Value("${authentication.service.url}")
	String authServiceUrl;

	@Autowired
    @Qualifier("myRestTemplate")
	RestTemplate restTemplate;

	public void register(AuthenticationRequest authRequest) {
		HttpEntity<AuthenticationRequest> request = new HttpEntity<AuthenticationRequest>(authRequest);
		restTemplate.exchange(authServiceUrl + "/register", HttpMethod.POST, request, Void.class);
	}
	
	public AuthenticationResource signin(AuthenticationRequest authRequest) {
		HttpEntity<AuthenticationRequest> request = new HttpEntity<AuthenticationRequest>(authRequest);
		return restTemplate.exchange(authServiceUrl + "/signin", HttpMethod.POST, request, AuthenticationResource.class).getBody();
	}
	
	public void updatePassword(UserCredentialsUpdateRequest updateAuthRequest) {
		HttpEntity<UserCredentialsUpdateRequest> request = new HttpEntity<UserCredentialsUpdateRequest>(updateAuthRequest);
		restTemplate.exchange(authServiceUrl + "/update", HttpMethod.PUT, request, void.class);
	}

}
