package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PropositionResource {
	
	public Long id;
	
	public Long idProject;

	@JsonInclude(value  = Include.NON_EMPTY)
	public Long idParentProposition;

	public String idUser;
    
	public String name;
    
	public String description;

	public String propositionType;

	public LocalDateTime submitDate;

	public Long delay;

	public Long evaluateDelay;
    
	public String status;

	public List<PropositionResource> amendments;

	public List<PropositionVoteResource> propositionVotes;
	
}
