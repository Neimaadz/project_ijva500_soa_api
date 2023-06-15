package com.cedalanavi.project_ijva500_soa_api.Project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.Project;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectSetProjectsRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectSetTeamsRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Services.ProjectService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/service-project/manage-project")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@GetMapping("")
	public Project[] getAll() {
		return projectService.getAll();
	}

	@PostMapping("/create")
	@Operation(summary = "Create project")
	public void create(@RequestBody ProjectCreateRequest projectCreateRequest) {
		projectService.create(projectCreateRequest);
	}

	@PutMapping("{id}/update")
	public ProjectUpdateRequest update(@RequestBody ProjectUpdateRequest projectRequest, @PathVariable int id) {
		return projectService.update(projectRequest, id);
	}

	@PutMapping(path = "{id}/teams")
	public void setTeams(@PathVariable int id, @RequestBody ProjectSetTeamsRequest projectSetTeamsRequest) {
		projectService.setTeams(id, projectSetTeamsRequest);
	}

	@PutMapping(path = "{id}/projects")
	public void setProjects(@PathVariable int id, @RequestBody ProjectSetProjectsRequest projectSetProjectsRequest) {
		projectService.setProjects(id, projectSetProjectsRequest);
	}

}
