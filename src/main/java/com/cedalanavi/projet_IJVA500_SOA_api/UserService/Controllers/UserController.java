package com.cedalanavi.projet_IJVA500_SOA_api.UserService.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cedalanavi.projet_IJVA500_SOA_api.UserService.Data.CreateUserRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.UserService.Data.UpdateUserRequest;
import com.cedalanavi.projet_IJVA500_SOA_api.UserService.Services.UserService;

@RestController
@RequestMapping("/service-user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/create")
	public void createUser(@RequestBody CreateUserRequest userRequest) {
		userService.createUser(userRequest);
	}
	
	@PutMapping("/update/{id}")
	public void updateUser(@RequestBody UpdateUserRequest userRequest, @PathVariable int id) {
		userService.updateUser(userRequest, id);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
	}

}
