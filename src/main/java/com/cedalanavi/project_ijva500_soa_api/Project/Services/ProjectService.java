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

import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectResource;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectSetProjectsRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectSetTeamsRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectUpdateRequest;

@Service
public class ProjectService {

	@Value("${project.service.url}/manage-project")
	String projectServiceUrl;

	RestTemplate restTemplate = new RestTemplate();

	public List<ProjectResource> getAll() {
		ResponseEntity<List<ProjectResource>> response = restTemplate.exchange(projectServiceUrl, HttpMethod.GET,  null, new ParameterizedTypeReference<List<ProjectResource>>(){});
		List<ProjectResource> project = response.getBody();
		return project;
	}

	public void create(ProjectCreateRequest projectCreateRequest) {
		HttpEntity<ProjectCreateRequest> request = new HttpEntity<ProjectCreateRequest>(projectCreateRequest);
		restTemplate.exchange(projectServiceUrl + "/create", HttpMethod.POST, request, ProjectCreateRequest.class);
	}

	public ProjectUpdateRequest update(ProjectUpdateRequest projectUpdateRequest, int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProjectUpdateRequest> request = new HttpEntity<ProjectUpdateRequest>(projectUpdateRequest, headers);
		HttpEntity<ProjectUpdateRequest> response = restTemplate.exchange(projectServiceUrl + "/" + id + "/update",
				HttpMethod.PUT, request, ProjectUpdateRequest.class);
		return response.getBody();
	}

	public void setTeams(int id, ProjectSetTeamsRequest projectSetTeamsRequest) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProjectSetTeamsRequest> request = new HttpEntity<ProjectSetTeamsRequest>(projectSetTeamsRequest, headers);
		restTemplate.exchange(projectServiceUrl + "/" + id + "/teams", HttpMethod.PUT, request, ProjectSetTeamsRequest.class);
	}

	public void setProjects(int id, ProjectSetProjectsRequest projectSetProjectsRequest) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProjectSetProjectsRequest> request = new HttpEntity<ProjectSetProjectsRequest>(projectSetProjectsRequest, headers);
		restTemplate.exchange(projectServiceUrl + "/" + id + "/projects",	HttpMethod.PUT, request, ProjectUpdateRequest.class);
	}

}
