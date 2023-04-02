package com.cedalanavi.project_ijva500_soa_api.User.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserUpdateRequest {
	
	@JsonIgnore(value = false)
	public String username;
}
