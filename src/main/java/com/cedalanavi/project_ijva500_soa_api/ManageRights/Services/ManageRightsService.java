package com.cedalanavi.project_ijva500_soa_api.ManageRights.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
		return restTemplate.exchange(manageUserRightsServiceUrl + "/user/add", HttpMethod.POST, request, ManageRightsResource.class).getBody();
	}
	
	public ManageRightsResource updateUserRights(UserRightsRequest userRightsRequest) {
		userRightsRequest.username = userService.getUser(userRightsRequest.idUser).username;
		HttpEntity<UserRightsRequest> request = new HttpEntity<UserRightsRequest>(userRightsRequest);
		return restTemplate.exchange(manageUserRightsServiceUrl + "/user/update", HttpMethod.PUT, request, ManageRightsResource.class).getBody();
	}
}
