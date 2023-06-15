package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import com.cedalanavi.project_ijva500_soa_api.utils.VoteTypes;

public class PropositionVoteResource {
	
	public Long id;

	public String idUser;

	public VoteTypes voteType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public VoteTypes getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteTypes voteType) {
		this.voteType = voteType;
	}
	
}
