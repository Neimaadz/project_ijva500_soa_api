package com.cedalanavi.project_ijva500_soa_api.Project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.TypeTeam;
import com.cedalanavi.project_ijva500_soa_api.Project.Data.TypeTeamCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.Project.Services.TypeTeamService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/service-project/manage-typeteam")
public class TypeTeamController {

	@Autowired
	private TypeTeamService typeTeamService;


	@GetMapping("")
	public TypeTeam[] getAll() {
		return typeTeamService.getAll();
	}

	@PostMapping("/create")
	@Operation(summary = "Create Type Team")
	public void create(@RequestBody TypeTeamCreateRequest typeTeamCreateRequest) {
		typeTeamService.create(typeTeamCreateRequest);
	}
}
