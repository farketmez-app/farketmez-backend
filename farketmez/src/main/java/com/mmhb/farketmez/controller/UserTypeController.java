package com.mmhb.farketmez.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.service.UserTypeService;

@RestController
@RequestMapping(value = "/usertypes")
public class UserTypeController {

	private final UserTypeService userTypeService;

	public UserTypeController(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

	/*
	 * example GET request to http://localhost:8080/usertypes
	 * 
	 * response: List of all user types
	 */
	@GetMapping
	public Object getAllUserTypes() {
		return this.userTypeService.getAllUserTypes();
	}

	/*
	 * example GET request to http://localhost:8080/usertypes/1
	 * 
	 * response: UserType with id 1
	 */
	@GetMapping(value = "/{id}")
	public UserType getUserTypeById(@PathVariable Long id) {
		return this.userTypeService.getUserTypeById(id).orElse(null);
	}

	/**
	 * example POST request to http://localhost:8080/usertypes/save with body: {
	 * "type": "admin" }
	 * 
	 * response: UserType object || null if not saved
	 */
	@PostMapping(value = "save")
	public UserType saveUserType(@RequestBody UserType userType) {
		return this.userTypeService.createUserType(userType);
	}

	/*
	 * example DELETE request to http://localhost:8080/usertypes/1
	 * 
	 * response: "UserType deleted" || "UserType not found"
	 */
	@DeleteMapping(value = "/{id}")
	public String deleteUserType(@PathVariable Long id) {
		if (this.userTypeService.getUserTypeById(id).isPresent()) {
			this.userTypeService.deleteUserType(id);
			return "UserType deleted";
		}
		return "UserType not found";
	}
}
