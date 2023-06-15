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

@RestController
@RequestMapping("/service-project/manage-requestjointeam")
public class RequestJoinTeamController {

	@Autowired
	private RequestJoinTeamService requestJoinTeamService;

	@GetMapping("")
	public RequestJoinTeam[] getAll() {
		return requestJoinTeamService.getAll();
	}

	@PostMapping(path = "/create") 
	public void create(@RequestBody RequestJoinTeamCreateRequest requestJoinTeamCreateRequest) {
		requestJoinTeamService.create(requestJoinTeamCreateRequest);
	}
	
	@PutMapping("{id}/response")
	public void response(@RequestBody RequestJoinTeamUpdateRequest requestJoinTeamUpdateRequest, @PathVariable int id) {
		requestJoinTeamService.update(id, requestJoinTeamUpdateRequest);
	}
}
