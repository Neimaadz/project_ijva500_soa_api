package com.cedalanavi.project_ijva500_soa_api.Project.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TypeTeamCreateRequest {

	@JsonIgnore(value = false)
	public String name;

	public String description;

}
