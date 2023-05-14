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
@Tag(name = "User Controller", description = "<h4>${SWAGGER.TAG.CONDITION.REQUIRED}</h4>")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping("/users")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Get all users informations",
			description = "<h2>Retrieve all users informations</h2>",
			responses = @ApiResponse(responseCode = "200", description = "Successfully retrieved all users informations")
	)
	public List<UserResource> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/user/{idUser}")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Get user informations by id user",
			description = "<h2>Retrieve the user informations by giving the id user</h2>",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully retrieved the user informations"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public UserResource getUser(@PathVariable String idUser) {
		return userService.getUser(idUser);
	}

	@PutMapping("/update/{idUser}")
	@Operation(
			hidden = true,
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Update user informations by id",
			description = "<h2>Update the user informations by giving the id user</h2>",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully updated the user informations"),
					@ApiResponse(
							responseCode = "500",
							description = "Throw an exception",
							content = @Content(schema = @Schema(hidden = true)))
					}
	)
	public void updateUser(@PathVariable String idUser, @RequestBody UserUpdateRequest userUpdateRequest) {
		userService.updateUser(idUser, userUpdateRequest);
	}

	@DeleteMapping("/delete/{idUser}")
	@Operation(
			summary = "${SWAGGER.TAG.ACCESS.CONTROLED} Delete user by id user",
			description = "<h2>Delete user by id user</h2>"
					+ "<h4>Delete the user from all services by giving the id user</h4>",
			responses = @ApiResponse(responseCode = "200", description = "Successfully deleted the user informations")
	)
	public void deleteUser(@PathVariable String idUser) {
		userService.deleteUser(idUser);
	}

}
