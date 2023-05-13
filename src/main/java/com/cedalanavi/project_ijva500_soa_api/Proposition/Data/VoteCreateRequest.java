package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import io.swagger.v3.oas.annotations.Hidden;

public class VoteCreateRequest {
	
	@Hidden
	public String idUser;

	public Long idProposition;

	@Hidden
	public String voteType;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
}
