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

import com.mmhb.farketmez.dto.UserInterestDTO;
import com.mmhb.farketmez.service.UserInterestService;

@RestController
@RequestMapping(value = "/userinterests")
public class UserInterestController {

	private final UserInterestService userInterestService;

	public UserInterestController(UserInterestService userInterestService) {
		this.userInterestService = userInterestService;
	}

	@GetMapping
	public List<UserInterestDTO> getAllUserInterests() {
		return userInterestService.findAll();
	}

	@GetMapping("/{id}")
	public UserInterestDTO getUserInterestById(@PathVariable Long id) {
		return userInterestService.findById(id);
	}

	@PostMapping("/create")
	public UserInterestDTO createUserInterest(@RequestBody UserInterestDTO userInterestDTO) {
		return userInterestService.createUserInterest(userInterestDTO);
	}

	@PutMapping("/{id}")
	public UserInterestDTO updateUserInterest(@PathVariable Long id, @RequestBody UserInterestDTO userInterestDTO) {
		return userInterestService.updateUserInterest(id, userInterestDTO);
	}

	@DeleteMapping("/{id}")
	public void deleteUserInterest(@PathVariable Long id) {
		userInterestService.deleteById(id);
	}
}
