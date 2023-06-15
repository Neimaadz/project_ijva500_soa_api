package com.cedalanavi.project_ijva500_soa_api.Project.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RequestJoinTeam{

	public int id;
	
	@JsonIgnore(value = false)
	public int teamId;

	@JsonIgnore(value = false)
	public String userId;
	
	@JsonIgnore(value = false)
	public String status;
}
