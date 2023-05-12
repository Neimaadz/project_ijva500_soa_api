package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import io.swagger.v3.oas.annotations.Hidden;

public class PropositionCreateRequest {
	
	@Hidden
	public String idUser;

	public Long idProject;
	
	public String name;
	
	public String description;

	@Hidden
	public String propositionType;

	public Long delay;

	public Long evaluateDelay;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
}
