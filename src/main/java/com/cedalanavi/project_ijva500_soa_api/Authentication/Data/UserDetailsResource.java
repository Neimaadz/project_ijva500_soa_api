package com.cedalanavi.project_ijva500_soa_api.Authentication.Data;

import java.util.List;

import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ReferentialUserRight;

public class UserDetailsResource {

	String idUser;
	
	String username;

	public List<ReferentialUserRight> referentialUserRights;

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

	public List<ReferentialUserRight> getReferentialUserRights() {
		return referentialUserRights;
	}

	public void setReferentialUserRights(List<ReferentialUserRight> referentialUserRights) {
		this.referentialUserRights = referentialUserRights;
	}
	
}
