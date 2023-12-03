package com.mmhb.farketmez.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/usertypes")
public class UserTypeController {

	private final UserTypeService userTypeService;

	@GetMapping
	public ResponseEntity<List<UserTypeDTO>> getAllUserTypes() {
		List<UserTypeDTO> userTypes = userTypeService.getAllUserTypes();
		return new ResponseEntity<>(userTypes, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserTypeDTO> getUserTypeById(@PathVariable Long id) {
		UserTypeDTO userTypeDTO = userTypeService.getUserTypeById(id);
		if (userTypeDTO != null) {
			return new ResponseEntity<>(userTypeDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<UserTypeDTO> createUserType(@RequestBody UserTypeDTO userTypeDTO) {
		UserTypeDTO createdUserType = userTypeService.createUserType(userTypeDTO);
		if (createdUserType != null) {
			return new ResponseEntity<>(createdUserType, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<UserTypeDTO> updateUserType(@RequestBody UserTypeDTO userTypeDTO) {
		UserTypeDTO updatedUserType = userTypeService.updateUserType(userTypeDTO);
		if (updatedUserType != null) {
			return new ResponseEntity<>(updatedUserType, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public String deleteUserType(@PathVariable Long id) {
		userTypeService.deleteUserType(id);
		return "UserType deleted";
	}
}
