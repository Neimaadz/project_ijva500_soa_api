package com.cedalanavi.project_ijva500_soa_api.ManageRights.Data;

import java.util.List;

import io.swagger.v3.oas.annotations.Hidden;

public class UserRightsRequest {
	
	public int idUser;

	@Hidden
	public String username;
	
	public List<String> referentialUserRights;
}
