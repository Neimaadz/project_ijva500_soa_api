package com.cedalanavi.project_ijva500_soa_api.Project.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeamType {
	
	@JsonIgnore(value = false)
	public int id;
	
	@JsonIgnore(value = false)
	public String name;

	public String description;

}
