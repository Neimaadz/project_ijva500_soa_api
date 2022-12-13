package com.cedalanavi.project_ijva500_soa_api.User.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserCreateRequest {

	@JsonIgnore(value = false)
	public String username;
	
	@JsonIgnore(value = false)
	public String token;
}
