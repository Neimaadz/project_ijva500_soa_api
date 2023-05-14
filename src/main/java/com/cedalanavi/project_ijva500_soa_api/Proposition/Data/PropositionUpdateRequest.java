package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import io.swagger.v3.oas.annotations.Hidden;

public class PropositionUpdateRequest {
	
	public String name;
	
	public String description;
	
	@Hidden
	public String status;

	public Long delay;

	public Long evaluateDelay;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
