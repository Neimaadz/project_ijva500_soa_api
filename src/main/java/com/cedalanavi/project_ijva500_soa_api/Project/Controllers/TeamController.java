package com.cedalanavi.project_ijva500_soa_api.Project.Controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.TeamCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.TeamResource;
import com.cedalanavi.project_ijva500_soa_api.Project.Services.TeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/service-project/manage-team")
@Tag(name = "Team Controller")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@GetMapping
	@Operation(
			summary = "Get all teams",
			description = "<h2>Retrieve all the teams</h2> ",
			responses = @ApiResponse(
					responseCode = "200",
					description = "All teams retrieved")
	)
	public List<TeamResource> getAll() {
		return teamService.getAll();
	}
	
//	@GetMapping(path = "{team_id}")
//	public List<Team> getById(@PathVariable Integer team_id, HttpServletResponse response) {
//		return teamService.getAllByProjectId(team_id);
//	}

	@PostMapping(path = "/create")
	@Operation(
			summary = "Create a new team",
			description = "<h2>Create a new team</h2> ",
			responses = @ApiResponse(
					responseCode = "200",
					description = "The new team have been succesfully created")
	)
	public void create(@RequestBody TeamCreateRequest teamRequest, HttpServletResponse response){
		teamService.create(teamRequest);
	}

//	@PutMapping(path = "{team_id}/users")
//	public Team setUsers(@PathVariable int team_id, @RequestBody TeamUpdateRequest teamUpdateUsersRequest) {
//	}

//	@PutMapping("{team_id}/update")
//	public void update(@PathVariable int team_id, @RequestBody TeamUpdateRequest teamUpdateUsersRequest, HttpServletResponse response) {
//	}
}
