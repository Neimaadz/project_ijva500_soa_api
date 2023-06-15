package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.cedalanavi.project_ijva500_soa_api.Project.Data.ProjectResource;
import com.cedalanavi.project_ijva500_soa_api.utils.PropositionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PropositionResource {
	
	public Long id;
	
	public Long idProject;
	
	public ProjectResource project;

	@JsonInclude(value  = Include.NON_EMPTY)
	public Long idParentProposition;

	public String idUser;
    
	public String name;
    
	public String description;

	public String propositionType;

	public LocalDateTime submitDate;

	public Long delay;

	public Long evaluateDelay;
    
	public PropositionStatus status;

	public List<PropositionResource> amendments;

	public List<PropositionVoteResource> propositionVotes;

	public List<CommentaryResource> commentaries;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProject() {
		return idProject;
	}

	public void setIdProject(Long idProject) {
		this.idProject = idProject;
	}

	public ProjectResource getProject() {
		return project;
	}

	public void setProject(ProjectResource project) {
		this.project = project;
	}

	public Long getIdParentProposition() {
		return idParentProposition;
	}

	public void setIdParentProposition(Long idParentProposition) {
		this.idParentProposition = idParentProposition;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPropositionType() {
		return propositionType;
	}

	public void setPropositionType(String propositionType) {
		this.propositionType = propositionType;
	}

	public LocalDateTime getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(LocalDateTime submitDate) {
		this.submitDate = submitDate;
	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}

	public Long getEvaluateDelay() {
		return evaluateDelay;
	}

	public void setEvaluateDelay(Long evaluateDelay) {
		this.evaluateDelay = evaluateDelay;
	}

	public PropositionStatus getStatus() {
		return status;
	}

	public void setStatus(PropositionStatus status) {
		this.status = status;
	}

	public List<PropositionResource> getAmendments() {
		return amendments;
	}

	public void setAmendments(List<PropositionResource> amendments) {
		this.amendments = amendments;
	}

	public List<PropositionVoteResource> getPropositionVotes() {
		return propositionVotes;
	}

	public void setPropositionVotes(List<PropositionVoteResource> propositionVotes) {
		this.propositionVotes = propositionVotes;
	}

	public List<CommentaryResource> getCommentaries() {
		return commentaries;
	}

	public void setCommentaries(List<CommentaryResource> commentaries) {
		this.commentaries = commentaries;
	}
	
}
