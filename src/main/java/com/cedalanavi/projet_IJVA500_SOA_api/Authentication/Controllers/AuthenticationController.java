package com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Data.AuthCredentialsUpdateRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Data.AuthenticationRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Data.AuthenticationResource;
import com.cedalanavi.projet_IJVA500_SOA_api.Authentication.Services.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/service-auth")
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping("/register")
	@Operation(summary = "Register me and Create user")
	public void register(@RequestBody AuthenticationRequest authenticationRequest) {
		authenticationService.register(authenticationRequest);
	}
	
	@PostMapping("/signin")
	@Operation(summary = "To connect and get JWT Token")
	public AuthenticationResource signin(@RequestBody AuthenticationRequest authRequest) {
		return authenticationService.signin(authRequest);
	}
	
	@PutMapping("/updateCredentials")
	@Operation(summary = "Update my credentials", security = @SecurityRequirement(name = "bearerAuth"))
	public void updateUserCredentials(@RequestBody AuthCredentialsUpdateRequest userCredentialsUpdateRequest) {
		authenticationService.updateUserCredentials(userCredentialsUpdateRequest);
	}

}
