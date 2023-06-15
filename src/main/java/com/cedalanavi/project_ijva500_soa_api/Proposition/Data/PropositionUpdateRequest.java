package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import com.cedalanavi.project_ijva500_soa_api.utils.PropositionStatus;

import io.swagger.v3.oas.annotations.Hidden;

public class PropositionUpdateRequest {
	
	public String name;
	
	public String description;
	
	@Hidden
	public PropositionStatus status;

	public Long delay;

	public Long evaluateDelay;

	public PropositionStatus getStatus() {
		return status;
	}

	public void setStatus(PropositionStatus status) {
		this.status = status;
	}
	
}
