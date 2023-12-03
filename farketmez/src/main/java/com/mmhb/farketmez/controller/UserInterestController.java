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

import com.mmhb.farketmez.dto.UserInterestDTO;
import com.mmhb.farketmez.service.UserInterestService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/userinterests")
public class UserInterestController {

	private final UserInterestService userInterestService;

	@GetMapping
	public ResponseEntity<List<UserInterestDTO>> getAllUserInterests() {
		List<UserInterestDTO> userInterests = userInterestService.findAll();
		return new ResponseEntity<>(userInterests, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserInterestDTO> getUserInterestById(@PathVariable Long id) {
		UserInterestDTO userInterestDTO = userInterestService.findById(id);
		if (userInterestDTO != null) {
			return new ResponseEntity<>(userInterestDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<UserInterestDTO> createUserInterest(@RequestBody UserInterestDTO userInterestDTO) {
		UserInterestDTO createdUserInterest = userInterestService.createUserInterest(userInterestDTO);
		if (createdUserInterest != null) {
			return new ResponseEntity<>(createdUserInterest, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserInterestDTO> updateUserInterest(@PathVariable Long id,
			@RequestBody UserInterestDTO userInterestDTO) {
		UserInterestDTO updatedUserInterest = userInterestService.updateUserInterest(id, userInterestDTO);
		if (updatedUserInterest != null) {
			return new ResponseEntity<>(updatedUserInterest, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteUserInterest(@PathVariable Long id) {
		userInterestService.deleteById(id);
	}
}
