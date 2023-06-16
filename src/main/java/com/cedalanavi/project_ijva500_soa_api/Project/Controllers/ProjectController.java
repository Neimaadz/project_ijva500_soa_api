package com.cedalanavi.project_ijva500_soa_api.Project.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectResource;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectSetProjectsRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectSetTeamsRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Services.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/service-project/manage-project")
@Tag(name = "Project Controller")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@GetMapping
	@Operation(
			summary = "Get all projects",
			description = "<h2>Retrieve all projects</h2> ",
			responses = @ApiResponse(
					responseCode = "200",
					description = "All projects have been retrieved")
	)
	public List<ProjectResource> getAll() {
		return projectService.getAll();
	}

	@PostMapping("/create")
	@Operation(
			summary = "Create a new project",
			description = "<h2>Create a new project</h2> ",
			responses = @ApiResponse(
					responseCode = "200",
					description = "The new project have been succesfully created")
	)
	public void create(@RequestBody ProjectCreateRequest projectCreateRequest) {
		projectService.create(projectCreateRequest);
	}

	@PutMapping("{idProject}/update")
	@Operation(
			summary = "Update a project",
			description = "<h2>Update a project by giving id</h2> ",
			parameters = {
					@Parameter(name = "idProject", description = "The id of the project to update")
			},
			responses = @ApiResponse(
					responseCode = "200",
					description = "Project have been succesfully updated")
	)
	public ProjectUpdateRequest update(@PathVariable int idProject, @RequestBody ProjectUpdateRequest projectRequest) {
		return projectService.update(projectRequest, idProject);
	}

	@PutMapping(path = "{idProject}/teams")
	@Operation(
			summary = "Set sub teams to a project",
			description = "<h2>Add multiple teams to a project by giving the id</h2> ",
			parameters = {
					@Parameter(name = "idProject", description = "Id project")
			},
			responses = @ApiResponse(
					responseCode = "200",
					description = "Sub teams have been succesfully added")
	)
	public void setTeams(@PathVariable int idProject, @RequestBody ProjectSetTeamsRequest projectSetTeamsRequest) {
		projectService.setTeams(idProject, projectSetTeamsRequest);
	}

	@PutMapping(path = "{idProject}/projects")
	@Operation(
			summary = "Set sub projects to a project",
			description = "<h2>Add multiple sub projects to a project by giving the id</h2> ",
			parameters = {
					@Parameter(name = "idProject", description = "Id project")
			},
			responses = @ApiResponse(
					responseCode = "200",
					description = "Sub projects have been succesfully added")
	)
	public void setProjects(@PathVariable int idProject, @RequestBody ProjectSetProjectsRequest projectSetProjectsRequest) {
		projectService.setProjects(idProject, projectSetProjectsRequest);
	}

}
