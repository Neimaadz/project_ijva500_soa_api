package com.cedalanavi.project_ijva500_soa_api.Project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.TeamType;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.TypeTeamCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Services.TypeTeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/service-project/manage-typeteam")
@Tag(name = "Team Controller")
public class TypeTeamController {

	@Autowired
	private TypeTeamService typeTeamService;


	@GetMapping
	@Operation(
			summary = "Get types teams referentials",
			description = "<h2>Retrieve types teams referentials</h2> ",
			responses = @ApiResponse(
					responseCode = "200",
					description = "All types team sreferentials retrieved")
	)
	public TeamType[] getAll() {
		return typeTeamService.getAll();
	}

	@PostMapping("/create")
	@Operation(
			summary = "Create new type team referential",
			description = "<h2>Add a new type team to the referential</h2> ",
			responses = @ApiResponse(
					responseCode = "200",
					description = "New type team succesfully added to the referential")
	)
	public void create(@RequestBody TypeTeamCreateRequest typeTeamCreateRequest) {
		typeTeamService.create(typeTeamCreateRequest);
	}
}
