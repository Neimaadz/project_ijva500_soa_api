package com.cedalanavi.project_ijva500_soa_api.Authentication.Data;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class AuthInformationResource {
	
	String idUser;
	
	String username;
	
	Collection<? extends GrantedAuthority> autorities = new ArrayList<GrantedAuthority>();

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Collection<? extends GrantedAuthority> getAutorities() {
		return autorities;
	}

	public void setAutorities(Collection<? extends GrantedAuthority> collection) {
		this.autorities = collection;
	}
	
}
