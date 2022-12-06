package com.cedalanavi.projet_IJVA500_SOA_api.User.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.projet_IJVA500_SOA_api.User.Data.UserUpdateRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.User.Services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/service-user")
public class UserController {
	
	@Autowired
	UserService userService;

	@PutMapping("/update/{id}")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public void updateUser(@RequestBody UserUpdateRequest userRequest, @PathVariable int id) {
		userService.updateUser(userRequest, id);
	}

	@DeleteMapping("/delete/{id}")
	@Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
	public void deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
	}

}
