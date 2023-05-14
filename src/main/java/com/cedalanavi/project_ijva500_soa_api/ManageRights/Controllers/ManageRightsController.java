package com.cedalanavi.project_ijva500_soa_api.ManageRights.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ManageRightsResource;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ReferentialUserRight;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ReferentialUserRightCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.ReferentialUserRightUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.UserRightsCreateRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Data.UserRightsUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.ManageRights.Services.ManageRightsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("manage-rights")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Manage Rights Controller", description = "<h4>${SWAGGER.TAG.CONDITION.REQUIRED}</h4>")
public class ManageRightsController {
	
	@Autowired
	ManageRightsService manageRightsService;
	
	@GetMapping("/user-referentials")
	@Operation(
			summary = "Get all referentials of user rights",
			description = "<h2>Retrieve all referentials of user rights</h2>",
			responses = @ApiResponse(responseCode = "200", description = "Successfully retrieved all referentials of user rights")
	)
	public List<ReferentialUserRight> getAllUserRightReferentials() {
		return manageRightsService.getAllUserRightReferentials();
	}

	@PostMapping("/user-referentials/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Add multiples referentials of user rights",
			description = "<h2>Append multiple user rights to the referential</h2>",
			responses = @ApiResponse(responseCode = "200", description = "Successfully added to the user rights referentials")
	)
	public List<ReferentialUserRight> addUserRightReferentials(@RequestBody(required = true) ReferentialUserRightCreateRequest referentialUserRightCreateRequest) {
		return manageRightsService.addUserRightReferentials(referentialUserRightCreateRequest);
	}
	
	@PutMapping("/user-referentials/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Update referential of user rights",
			description = "<h2>Update one referential by giving the id of the right in request body</h2>",
			responses = @ApiResponse(responseCode = "200", description = "Successfully updated the referential of user rights")
	)
	public List<ReferentialUserRight> updateUserRightReferentials(@RequestBody ReferentialUserRightUpdateRequest referentialUserRightUpdateRequest) {
		return manageRightsService.updateUserRightReferentials(referentialUserRightUpdateRequest);
	}
	
	@GetMapping("/users")
	@Operation(
			summary = "Get user rights of all users",
			description = "<h2>Retrieve user rights of all users</h2>",
			responses = @ApiResponse(responseCode = "200", description = "Successfully retrieved user rights of all users")
	)
	public List<ManageRightsResource> getUserRightsOfUsers() {
		return manageRightsService.getUserRightsOfUsers();
	}

	@GetMapping("/user/{username}")
	@Operation(
			summary = "Get user rights by username",
			description = "<h2>Get user rights by username</h2>"
					+ "<h4>Retrieve the user rights informations by giving the username</h4>",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully retrieved the user rights"),
					@ApiResponse(responseCode = "500", description = "Throw an exception - user not existing", content = @Content(schema = @Schema(hidden = true)))}
	)
	public ManageRightsResource getUserRightsByUsername(@PathVariable @Parameter(name = "username", description = "The name of the user") String username) {
		return manageRightsService.getUserRightsByUsername(username);
	}
	
	@PostMapping("/user/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(
			hidden = true,
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Add user rights",
			description = "<h2>Add user rights</h2>"
					+ "<h4>Insert multiple user rights by giving the id user in the request body</h4>",
			responses = @ApiResponse(responseCode = "200", description = "Successfully added the user rights")
	)
	public ManageRightsResource addUserRights(@RequestBody UserRightsCreateRequest userRightsCreateRequest) {
		return manageRightsService.addUserRights(userRightsCreateRequest);
	}
	
	@PutMapping("/user/update/{idUser}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Update user rights",
			description = "<h2>Update multiple user rights by giving the id user in the request body</h2>",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully updated the user rights"),
					@ApiResponse(responseCode = "500", description = "Throw an exception - Referentials user rights not exist", content = @Content(schema = @Schema(hidden = true)))}
	)
	public ManageRightsResource updateUserRights(@PathVariable String idUser, @RequestBody UserRightsUpdateRequest userRightsUpdateRequest) {
		return manageRightsService.updateUserRights(idUser, userRightsUpdateRequest);
	}
}
