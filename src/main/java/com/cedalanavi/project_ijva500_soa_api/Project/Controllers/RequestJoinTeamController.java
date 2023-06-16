package com.cedalanavi.project_ijva500_soa_api.Project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.RequestJoinTeam;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.RequestJoinTeamCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.RequestJoinTeamUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Services.RequestJoinTeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/service-project/manage-requestjointeam")
@Tag(name = "Team Controller")
public class RequestJoinTeamController {

	@Autowired
	private RequestJoinTeamService requestJoinTeamService;

	@GetMapping
	@Operation(
			summary = "Get all resquests to join a team",
			description = "<h2>Retrieve all resquests to join a team</h2> ",
			responses = @ApiResponse(
					responseCode = "200",
					description = "All requests retrieved")
	)
	public RequestJoinTeam[] getAll() {
		return requestJoinTeamService.getAll();
	}

	@PostMapping(path = "/create") 
	@Operation(
			summary = "Create a new a request to join a team",
			description = "<h2>Create a new a request to join a team</h2> ",
			responses = @ApiResponse(
					responseCode = "200",
					description = "Request have successfully submitted")
	)
	public void create(@RequestBody RequestJoinTeamCreateRequest requestJoinTeamCreateRequest) {
		requestJoinTeamService.create(requestJoinTeamCreateRequest);
	}
	
	@PutMapping("{idRequest}/response")
	@Operation(
			summary = "Accept or reject a request to join a team",
			description = "<h2>Update the request join team demand</h2> ",
			parameters = {
					@Parameter(name = "idRequest", description = "The id of the request to join a team")
			},
			responses = @ApiResponse(
					responseCode = "200",
					description = "Request have successfully updated")
	)
	public void response(@PathVariable int idRequest, @RequestBody RequestJoinTeamUpdateRequest requestJoinTeamUpdateRequest) {
		requestJoinTeamService.update(idRequest, requestJoinTeamUpdateRequest);
	}
}
