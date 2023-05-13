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

import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ManageRightsResource;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ReferentialUserRight;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ReferentialUserRightCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ReferentialUserRightUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.UserRightsCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.UserRightsUpdateRequest;
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

	public List<ReferentialUserRight> addUserRightReferentials(ReferentialUserRightCreateRequest referentialUserRightCreateRequest) {
		HttpEntity<ReferentialUserRightCreateRequest> request = new HttpEntity<ReferentialUserRightCreateRequest>(referentialUserRightCreateRequest);
		return restTemplate.exchange(manageUserRightsServiceUrl + "/user-referentials/add", HttpMethod.POST, request, new ParameterizedTypeReference<List<ReferentialUserRight>>(){}).getBody();
	}

	public List<ReferentialUserRight> updateUserRightReferentials(ReferentialUserRightUpdateRequest referentialUserRightUpdateRequest) {
		HttpEntity<ReferentialUserRightUpdateRequest> request = new HttpEntity<ReferentialUserRightUpdateRequest>(referentialUserRightUpdateRequest);
		return restTemplate.exchange(manageUserRightsServiceUrl + "/user-referentials/update", HttpMethod.PUT, request, new ParameterizedTypeReference<List<ReferentialUserRight>>(){}).getBody();
	}

	public List<ManageRightsResource> getUserRightsOfUsers() {
		return restTemplate.exchange(manageUserRightsServiceUrl + "/users", HttpMethod.GET, null, new ParameterizedTypeReference<List<ManageRightsResource>>(){}).getBody();
	}
	
	public ManageRightsResource getUserRightsByUsername(String username) {
		return restTemplate.exchange(manageUserRightsServiceUrl + "/user/" + username, HttpMethod.GET, null, ManageRightsResource.class).getBody();
	}
	
	public ManageRightsResource addUserRights(UserRightsCreateRequest userRightsCreateRequest) {
		HttpEntity<UserRightsCreateRequest> request = new HttpEntity<UserRightsCreateRequest>(userRightsCreateRequest);
		ManageRightsResource manageRightsResource = restTemplate.exchange(manageUserRightsServiceUrl + "/user/add", HttpMethod.POST, request, ManageRightsResource.class).getBody();
		
		return manageRightsResource;
	}
	
	public ManageRightsResource updateUserRights(String idUser, UserRightsUpdateRequest userRightsUpdateRequest) {
		HttpEntity<UserRightsUpdateRequest> request = new HttpEntity<UserRightsUpdateRequest>(userRightsUpdateRequest);
		ManageRightsResource manageRightsResource = restTemplate.exchange(manageUserRightsServiceUrl + "/user/update/" + idUser, HttpMethod.PUT, request, ManageRightsResource.class).getBody();
		
		return manageRightsResource;
	}
}
