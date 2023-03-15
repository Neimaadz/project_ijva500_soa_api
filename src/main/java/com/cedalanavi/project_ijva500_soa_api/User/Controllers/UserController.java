package com.cedalanavi.project_ijva500_soa_api.User.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.project_ijva500_soa_api.User.Data.UserResource;
import com.cedalanavi.project_ijva500_soa_api.User.Data.UserUpdateRequest;
import com.cedalanavi.project_ijva500_soa_api.User.Services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/service-user")
@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
@Tag(name = "User Controller", description = "${SWAGGER.TAG.CONDITION.REQUIRED}")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping("/users")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED}. Get all users informations",
			description = "Retrieve all users informations",
			responses = @ApiResponse(responseCode = "200", description = "Successfully retrieved all users informations")
	)
	public List<UserResource> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/user/{id}")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED}. Get user informations by id user",
			description = "Retrieve the user informations by giving the id user",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully retrieved the user informations"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public UserResource getUser(@PathVariable int id) {
		return userService.getUser(id);
	}

	@PutMapping("/update/{id}")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED}. Update user informations by id",
			description = "Update the user informations by giving the id user",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully updated the user informations"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public void updateUser(@RequestBody UserUpdateRequest userRequest, @PathVariable int id) {
		userService.updateUser(userRequest, id);
	}

	@DeleteMapping("/delete/{id}")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED}. Delete user by id user",
			description = "Delete the user by giving the id user",
			responses = @ApiResponse(responseCode = "200", description = "Successfully deleted the user informations")
	)
	public void deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
	}

}
