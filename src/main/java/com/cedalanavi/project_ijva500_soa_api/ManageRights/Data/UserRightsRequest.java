package com.cedalanavi.project_ijva500_soa_api.ManageRights.Data;

import java.util.List;

import io.swagger.v3.oas.annotations.Hidden;

public class UserRightsRequest {
	
	public String idUser;

	@Hidden
	public String username;
	
	public List<String> referentialUserRights;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getReferentialUserRights() {
		return referentialUserRights;
	}

	public void setReferentialUserRights(List<String> referentialUserRights) {
		this.referentialUserRights = referentialUserRights;
	}
	
}
