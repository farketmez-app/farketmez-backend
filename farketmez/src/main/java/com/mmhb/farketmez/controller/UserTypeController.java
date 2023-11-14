package com.mmhb.farketmez.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.dto.UserTypeDTO;
import com.mmhb.farketmez.service.UserTypeService;

@RestController
@RequestMapping(value = "/usertypes")
public class UserTypeController {

	private final UserTypeService userTypeService;

	public UserTypeController(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

	@GetMapping
	public List<UserTypeDTO> getAllUserTypes() {
		return userTypeService.getAllUserTypes();
	}

	@GetMapping("/{id}")
	public UserTypeDTO getUserTypeById(@PathVariable Long id) {
		return userTypeService.getUserTypeById(id);
	}

	@PostMapping("/create")
	public UserTypeDTO createUserType(@RequestBody UserTypeDTO userTypeDTO) {
		return userTypeService.createUserType(userTypeDTO);
	}

	@PutMapping("/{id}")
	public UserTypeDTO updateUserType(@PathVariable Long id, @RequestBody UserTypeDTO userTypeDTO) {
		return userTypeService.updateUserType(id, userTypeDTO);
	}

	@DeleteMapping("/{id}")
	public String deleteUserType(@PathVariable Long id) {
		userTypeService.deleteUserType(id);
		return "UserType deleted";
	}
}
