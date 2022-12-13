package com.cedalanavi.project_ijva500_soa_api.Authentication.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthenticationRequest {

	@JsonIgnore(value = false)
	public String username;
	
	@JsonIgnore(value = false)
	public String password;
}
