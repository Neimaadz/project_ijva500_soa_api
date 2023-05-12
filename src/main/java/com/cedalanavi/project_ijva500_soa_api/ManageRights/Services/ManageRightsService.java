package com.cedalanavi.project_ijva500_soa_api.ManageRights.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.AddReferentialUserRightRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ManageRightsResource;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ReferentialUserRight;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.UpdateReferentialUserRightRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.UserRightsRequest;
import com.cedalanavi.project_ijva500_soa_api.User.Services.UserService;

@Service
public class ManageRightsService {

	@Value("${manage.user.rights.service.url}")
	String manageUserRightsServiceUrl;
	
	@Autowired
    @Qualifier("myRestTemplate")
	RestTemplate restTemplate;
	
	@Autowired
	UserService userService;

	public List<ReferentialUserRight> getAllUserRightReferentials() {
		return restTemplate.exchange(manageUserRightsServiceUrl + "/user-referentials", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReferentialUserRight>>(){}).getBody();
	}

	public List<ReferentialUserRight> addUserRightReferentials(AddReferentialUserRightRequest addReferentialUserRightRequest) {
		HttpEntity<AddReferentialUserRightRequest> request = new HttpEntity<AddReferentialUserRightRequest>(addReferentialUserRightRequest);
		return restTemplate.exchange(manageUserRightsServiceUrl + "/user-referentials/add", HttpMethod.POST, request, new ParameterizedTypeReference<List<ReferentialUserRight>>(){}).getBody();
	}

	public List<ReferentialUserRight> updateUserRightReferentials(UpdateReferentialUserRightRequest updateReferentialUserRightRequest) {
		HttpEntity<UpdateReferentialUserRightRequest> request = new HttpEntity<UpdateReferentialUserRightRequest>(updateReferentialUserRightRequest);
		return restTemplate.exchange(manageUserRightsServiceUrl + "/user-referentials/update", HttpMethod.PUT, request, new ParameterizedTypeReference<List<ReferentialUserRight>>(){}).getBody();
	}

	public List<ManageRightsResource> getUserRightsOfUsers() {
		return restTemplate.exchange(manageUserRightsServiceUrl + "/users", HttpMethod.GET, null, new ParameterizedTypeReference<List<ManageRightsResource>>(){}).getBody();
	}
	
	public ManageRightsResource getUserRightsByUsername(String username) {
		return restTemplate.exchange(manageUserRightsServiceUrl + "/user/" + username, HttpMethod.GET, null, ManageRightsResource.class).getBody();
	}
	
	public ManageRightsResource addUserRights(UserRightsRequest userRightsRequest) {
		userRightsRequest.username = userService.getUser(userRightsRequest.idUser).username;
		HttpEntity<UserRightsRequest> request = new HttpEntity<UserRightsRequest>(userRightsRequest);
		ManageRightsResource manageRightsResource = restTemplate.exchange(manageUserRightsServiceUrl + "/user/add", HttpMethod.POST, request, ManageRightsResource.class).getBody();
		
	 	Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	 	manageRightsResource.referentialUserRights.forEach(userRight -> {
	 		grantedAuthorities.add(new SimpleGrantedAuthority(userRight.label));
        });
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		
		return manageRightsResource;
	}
	
	public ManageRightsResource updateUserRights(UserRightsRequest userRightsRequest) {
		userRightsRequest.username = userService.getUser(userRightsRequest.idUser).username;
		HttpEntity<UserRightsRequest> request = new HttpEntity<UserRightsRequest>(userRightsRequest);
		ManageRightsResource manageRightsResource = restTemplate.exchange(manageUserRightsServiceUrl + "/user/update", HttpMethod.PUT, request, ManageRightsResource.class).getBody();

	 	Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	 	manageRightsResource.referentialUserRights.forEach(userRight -> {
	 		grantedAuthorities.add(new SimpleGrantedAuthority(userRight.label));
        });
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		
		return manageRightsResource;
	}
}
