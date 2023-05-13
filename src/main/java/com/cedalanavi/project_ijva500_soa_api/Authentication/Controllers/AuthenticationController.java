package com.cedalanavi.project_ijva500_soa_api.Authentication.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.AuthCredentialsUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.AuthenticationRequest;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.AuthenticationResource;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Data.UserDetailsResource;
import com.cedalanavi.project_ijva500_soa_api.Authentication.Services.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/service-auth")
@PreAuthorize("isAuthenticated()")
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authenticationService;
	
	final String TAG_CONNECTION = "1. Connection";

	@GetMapping("/me")
	@Tag(name = TAG_CONNECTION)
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Current user",
			responses = {
					@ApiResponse(responseCode = "200", description = "Informations of current user connected"),
					@ApiResponse(responseCode = "500", description = "Throw an exception - not connected", content = @Content(schema = @Schema(hidden = true)))}
	)
	public UserDetailsResource Me(HttpServletRequest httpServletRequest) {
    	return authenticationService.currentSession(httpServletRequest);
	}
	
	@PostMapping("/register")
	@PreAuthorize("permitAll()")
	@Tag(name = TAG_CONNECTION)
	@Operation(
			summary = "Register me",
			description = "<h2>Sign Up</h2>",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully signed up"),
					@ApiResponse(responseCode = "500", description = "Throw an exception - username already taken")}
	)
	public void register(@RequestBody AuthenticationRequest authenticationRequest) {
		authenticationService.register(authenticationRequest);
	}
	
	@PostMapping("/signin")
	@PreAuthorize("permitAll()")
	@Tag(name = TAG_CONNECTION)
	@Operation(
			summary = "Sign in",
			description = "<h2>Sign in</h2> "
					+ "<h4>Sign in to get JWT Token and access to other API resources</h4>",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully signed in and got the token"),
					@ApiResponse(responseCode = "500", description = "Throw an exception - bad credentials", content = @Content(schema = @Schema(hidden = true)))}
	)
	public AuthenticationResource signin(HttpServletRequest httpServletRequest, @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
		return authenticationService.signin(httpServletRequest, authenticationRequest);
	}
	
	@GetMapping("/logout")
	@Tag(name = TAG_CONNECTION)
	@Operation(
			hidden = true,
			summary = "Logout",
			description = "Logout",
			responses = @ApiResponse(responseCode = "200", description = "Successfully logged out")
	)
	public void logout(HttpServletRequest request) {
		authenticationService.logout(request);
	}

	@PutMapping("/credentials/update/me")
	@Tag(name = "Authentication Controller", description = "<h4>${SWAGGER.TAG.CONDITION.REQUIRED}</h4>")
	@Operation(
			summary = "Update my credentials",
			description = "<h2>Update my account credentials</h2>",
			responses = @ApiResponse(responseCode = "200", description = "Successfully updated my account credentials")
	)
	public void updateUserCredentials(@RequestBody AuthCredentialsUpdateRequest authCredentialsUpdateRequest) {
		authenticationService.updateUserCredentials(authCredentialsUpdateRequest);
	}

}
