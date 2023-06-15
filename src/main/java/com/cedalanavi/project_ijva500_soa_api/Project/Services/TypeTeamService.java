package com.cedalanavi.project_ijva500_soa_api.Project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.TypeTeam;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.TypeTeamCreateRequest;

@Service
public class TypeTeamService {
	@Value("${project.service.url}/manage-typeteam")
	String projectServiceUrl;

	@Autowired
	@Qualifier("myRestTemplate")
	RestTemplate restTemplate;

	
	public TypeTeam[] getAll() {
		System.out.println(projectServiceUrl);
		ResponseEntity<TypeTeam[]> response = restTemplate.getForEntity(projectServiceUrl, TypeTeam[].class);
		TypeTeam[] typeTeams = response.getBody();
		return typeTeams;
	}
	
	public void create(TypeTeamCreateRequest typeTeamCreateRequest) 
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TypeTeamCreateRequest> request = new HttpEntity<TypeTeamCreateRequest>(typeTeamCreateRequest, headers);
		restTemplate.exchange(projectServiceUrl + "/create", HttpMethod.POST, request, TypeTeamCreateRequest.class);
	}
}
