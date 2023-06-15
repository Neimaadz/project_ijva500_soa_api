package com.cedalanavi.project_ijva500_soa_api.Project.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.TeamCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.TeamResource;

@Service
public class TeamService {

	@Value("${project.service.url}/manage-team")
	String projectServiceUrl;

	RestTemplate restTemplate = new RestTemplate();
	
	public List<TeamResource> getAll() {
		ResponseEntity<List<TeamResource>> response = restTemplate.exchange(projectServiceUrl, HttpMethod.GET,  null, new ParameterizedTypeReference<List<TeamResource>>(){});
		List<TeamResource> teams = response.getBody();
		return teams;
	}
	
//	public List<Team> getAllByProjectId(Integer project_id) {
//  }

	public void create(TeamCreateRequest teamCreateRequest){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TeamCreateRequest> request = new HttpEntity<TeamCreateRequest>(teamCreateRequest, headers);
		restTemplate.exchange(projectServiceUrl + "/create", HttpMethod.POST, request, TeamCreateRequest.class);
	}

//	public Team addUser(int team_id, int user_id) {
//	}

//	public Team setUsers(int team_id, TeamUpdateRequest teamUpdateUsersRequest) {
//	}

//	public Team update(int team_id, TeamUpdateRequest teamUpdateRequest) {
//	}
}
