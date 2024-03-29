package com.cedalanavi.project_ijva500_soa_api.Project.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.RequestJoinTeam;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.RequestJoinTeamCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.RequestJoinTeamUpdateRequest;

@Service
public class RequestJoinTeamService {
	@Value("${project.service.url}/manage-requestjointeam")
	String projectServiceUrl;

	RestTemplate restTemplate = new RestTemplate();
	
	public RequestJoinTeam[] getAll() {
		ResponseEntity<RequestJoinTeam[]> response = restTemplate.getForEntity(projectServiceUrl, RequestJoinTeam[].class);
		RequestJoinTeam[] requestJoinTeam = response.getBody();
		return requestJoinTeam;
	}

	public void create(RequestJoinTeamCreateRequest requestJoinTeamCreateRequest) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RequestJoinTeamCreateRequest> request = new HttpEntity<RequestJoinTeamCreateRequest>(requestJoinTeamCreateRequest, headers);
		restTemplate.exchange(projectServiceUrl + "/create", HttpMethod.POST, request, RequestJoinTeamCreateRequest.class);
	}
	
	public void update(int idRequest, RequestJoinTeamUpdateRequest requestJoinTeamUpdateRequest) {
		HttpEntity<RequestJoinTeamUpdateRequest> request = new HttpEntity<RequestJoinTeamUpdateRequest>(requestJoinTeamUpdateRequest);
		restTemplate.exchange(projectServiceUrl + "/" + idRequest + "/response", HttpMethod.PUT, request, RequestJoinTeamUpdateRequest.class);
	}

}
