package com.cedalanavi.project_ijva500_soa_api.Proposition.Data;

import io.swagger.v3.oas.annotations.Hidden;

public class CommentaryCreateRequest {

	@Hidden
	public String idUser;

	public String message;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
