package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.mmhb.farketmez.mapper.UserTypeMapper;
import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.service.UserTypeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/usertypes")
public class UserTypeController {

	private final UserTypeService userTypeService;

	@GetMapping
	public ResponseEntity<List<UserTypeDTO>> getAllUserTypes() {
		List<UserTypeDTO> userTypes = userTypeService.getAllUserTypes().stream().map(UserTypeMapper::toUserTypeDto)
				.collect(Collectors.toList());
		;
		return new ResponseEntity<>(userTypes, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserTypeDTO> getUserTypeById(@PathVariable Long id) {
		UserType userType = userTypeService.getUserTypeById(id);
		if (userType != null) {
			UserTypeDTO userTypeDTO = UserTypeMapper.toUserTypeDto(userType);
			return new ResponseEntity<>(userTypeDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<UserTypeDTO> createUserType(@RequestBody UserTypeDTO userTypeDTO) {
		UserType userType = UserTypeMapper.fromUserTypeDto(userTypeDTO);
		UserType createdUserType = userTypeService.createUserType(userType);
		return new ResponseEntity<>(UserTypeMapper.toUserTypeDto(createdUserType), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<UserTypeDTO> updateUserType(@RequestBody UserTypeDTO userTypeDTO) {
		UserType userType = UserTypeMapper.fromUserTypeDto(userTypeDTO);
		UserType updatedUserType = userTypeService.updateUserType(userType);
		if (updatedUserType != null) {
			return new ResponseEntity<>(UserTypeMapper.toUserTypeDto(updatedUserType), HttpStatus.OK);
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
