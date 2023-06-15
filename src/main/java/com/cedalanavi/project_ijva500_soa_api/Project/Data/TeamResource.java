package com.cedalanavi.project_ijva500_soa_api.Project.Data;

import java.util.List;

import javax.persistence.ElementCollection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeamResource {

	@JsonIgnore(value = false)
	public int id;
	
	@JsonIgnore(value = false)
	public String name;

	@JsonIgnore(value = false)
	public TeamType teamType;
	
	@ElementCollection
	public List<String> usersIds;
}
