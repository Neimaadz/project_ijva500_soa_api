package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import com.cedalanavi.project_ijva500_soa_api.utils.VoteType;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public class VoteCreateRequest {
	
	@Hidden
	public String idUser;

	public Long idProposition;

	@Hidden
	@Schema(implementation = VoteType.class)
	public String voteType;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
}
