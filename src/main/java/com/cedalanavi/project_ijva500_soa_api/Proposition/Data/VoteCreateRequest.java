package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import com.cedalanavi.project_ijva500_soa_api.utils.PropositionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.Hidden;

public class VoteCreateRequest {
	
	@Hidden
	public String idUser;

	@Hidden
	public String voteType;

	@Hidden
	@JsonInclude(value = Include.NON_EMPTY)
	public PropositionStatus status;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getVoteType() {
		return voteType;
	}

	public void setVoteType(String voteType) {
		this.voteType = voteType;
	}

	public PropositionStatus getStatus() {
		return status;
	}

	public void setStatus(PropositionStatus status) {
		this.status = status;
	}
	
}
