package com.cedalanavi.project_ijva500_soa_api.Project.Data;

import java.util.List;

import javax.persistence.ElementCollection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeamCreateRequest {
	@JsonIgnore(value = false)
	public String name;

	@JsonIgnore(value = false)
	public int typeTeamId;
	
	@ElementCollection
	public List<String> usersIds;
}
