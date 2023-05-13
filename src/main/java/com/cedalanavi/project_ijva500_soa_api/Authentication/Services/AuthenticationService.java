package com.cedalanavi.project_ijva500_soa_api.Authentication.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.AuthCredentialsUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.AuthenticationRequest;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.AuthenticationResource;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.UserDetailsResource;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ManageRightsResource;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.UserRightsCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Services.ManageRightsService;
import com.cedalanavi.project_ijva500_soa_api.User.Data.UserCreateRequest;

@Service
public class AuthenticationService {
	
	@Value("${authentication.service.url}")
	String authServiceUrl;
	
	@Value("${manage.user.rights.service.url}")
	String manageUserRightsServiceUrl;
	
	@Value("${user.service.url}")
	String userServiceUrl;

	@Autowired
    @Qualifier("myRestTemplate")
	RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	ManageRightsService manageRightsService;
	
	public UserDetailsResource currentSession(HttpServletRequest httpServletRequest) {
    	Object sessionAuthStored = httpServletRequest.getSession().getAttribute("Authorization");
		String jwtToken = sessionAuthStored.toString().substring(7);
		UserDetailsResource authenticated = isAuthenticated(jwtToken);
		authenticated.setReferentialUserRights(manageRightsService.getUserRightsByUsername(authenticated.getUsername()).referentialUserRights);
		
		return authenticated;
	}

	public void register(AuthenticationRequest authenticationRequest) {
		HttpEntity<AuthenticationRequest> authCreateRequest = new HttpEntity<AuthenticationRequest>(authenticationRequest);
		UserCreateRequest response = restTemplate.exchange(authServiceUrl + "/register", HttpMethod.POST, authCreateRequest, UserCreateRequest.class).getBody();
		
		if (response != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + response.token);
			HttpEntity<UserCreateRequest> userCreateRequest = new HttpEntity<UserCreateRequest>(response, headers);
			restTemplate.exchange(userServiceUrl + "/create", HttpMethod.POST, userCreateRequest, Void.class);

			UserRightsCreateRequest userRightsCreateRequest = new UserRightsCreateRequest();
			userRightsCreateRequest.setIdUser(response.idUser);
			userRightsCreateRequest.setUsername(response.username);
			userRightsCreateRequest.setReferentialUserRights(new ArrayList<String>());
			manageRightsService.addUserRights(userRightsCreateRequest);
		}
	}
	
	public AuthenticationResource signin(HttpServletRequest httpServletRequest, AuthenticationRequest authenticationRequest) throws AuthenticationException {
		// Call Authentication service to get token
		HttpEntity<AuthenticationRequest> authRequest = new HttpEntity<AuthenticationRequest>(authenticationRequest);
		AuthenticationResource authenticationResource = new RestTemplate().exchange(authServiceUrl + "/signin", HttpMethod.POST, authRequest, AuthenticationResource.class).getBody();
		final String token = authenticationResource.token;
		// Store token in session to use lately
		httpServletRequest.getSession().setAttribute("Authorization", "Bearer " + token);
		
		if (token != null) {
			// Call Authentication service to check token validity
			UserDetailsResource userDetailsResource = isAuthenticated(token);
			// Call Manage rights service to get user rights
			ManageRightsResource manageRightsResource = manageRightsService.getUserRightsByUsername(userDetailsResource.getUsername());
			
	        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	        manageRightsResource.referentialUserRights.forEach(userRight -> {
	        	grantedAuthorities.add(new SimpleGrantedAuthority(userRight.label));
	        });
	        UserDetails userDetail = new org.springframework.security.core.userdetails.User(userDetailsResource.getUsername(), "", grantedAuthorities);

	        if (userDetail.getUsername() != null) {
				setSecurityContextAuthentication(userDetail, httpServletRequest);
			}
		}
		
		return authenticationResource;
	}
	
	public void logout(HttpServletRequest httpServletRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // If user is already authenticated
		if (!(auth instanceof AnonymousAuthenticationToken)) {
	        new SecurityContextLogoutHandler().logout(httpServletRequest, null, null);
		}
	}
	
	public void updateUserCredentials(AuthCredentialsUpdateRequest authCredentialsUpdateRequest) {
		HttpEntity<AuthCredentialsUpdateRequest> request = new HttpEntity<AuthCredentialsUpdateRequest>(authCredentialsUpdateRequest);
		restTemplate.exchange(authServiceUrl + "/credentials/update", HttpMethod.PUT, request, void.class);
	}
	
	public UserDetailsResource isAuthenticated(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		HttpEntity<String> request = new HttpEntity<>("body", headers);
		
		return restTemplate.exchange(authServiceUrl + "/isAuthenticated", HttpMethod.GET, request, UserDetailsResource.class).getBody();
	}
	
	
	
	
	
	private void setSecurityContextAuthentication(UserDetails userDetail, HttpServletRequest httpServletRequest) {
		// Set user information got from Authentication service
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
		// Set the Authentication in Spring Security context
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}

}
