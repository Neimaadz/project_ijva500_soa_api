package com.cedalanavi.projet_IJVA500_SOA_api.UserService.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UpdateUserRequest {
	
	@JsonIgnore(value = false)
	public String password;
}
