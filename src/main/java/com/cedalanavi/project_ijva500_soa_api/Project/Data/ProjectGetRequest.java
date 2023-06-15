package com.cedalanavi.project_ijva500_soa_api.Project.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProjectGetRequest {

	@JsonIgnore(value = false)
	private int id;
	
	@JsonIgnore(value = false)
	public String name;
	
	@JsonIgnore(value = false)
	public String parent_id;
	
}
