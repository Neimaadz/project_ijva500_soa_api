package com.cedalanavi.project_ijva500_soa_api.Project.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ProjectResource {
 
	public int id;
	 
	public String name;

	@JsonInclude(value  = Include.NON_DEFAULT)
	public int idParentProject;

	@JsonInclude(value  = Include.NON_EMPTY)
	public List<TeamResource> teams;

	@JsonInclude(value  = Include.NON_EMPTY)
	public List<ProjectResource> projects;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name; 
	}
	
	public int getIdParentProject() {
		return idParentProject;
	}

	public void setIdParentProject(int idParentProject) {
		this.idParentProject = idParentProject;
	}

	public List<TeamResource> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamResource> teams) {
		this.teams = teams;
	}
	
	public List<ProjectResource> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectResource> projects) {
		this.projects = projects;
	}
}
