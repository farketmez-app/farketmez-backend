package com.mmhb.farketmez.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.service.UserInterestService;

@RestController
@RequestMapping(value = "/userinterests")
public class UserInterestController {

	private final UserInterestService userInterestService;

	public UserInterestController(UserInterestService userInterestService) {
		this.userInterestService = userInterestService;
	}

	/*
	 * Example GET request: http://localhost:8080/userinterests
	 * 
	 * Response: List of all user interests
	 */
	@GetMapping
	public List<UserInterest> getAllUserInterests() {
		return userInterestService.findAll();
	}

	/**
	 * Example POST request: http://localhost:8080/userinterests/save Request body:
	 * { "interestName": "Sports" }
	 * 
	 * Response: User interest object or null if not saved
	 */
	@PostMapping("/save")
	public UserInterest save(@RequestBody UserInterest interest) {

		return userInterestService.save(interest);

	}

	/*
	 * Example DELETE request: http://localhost:8080/userinterests/1
	 * 
	 * Response: "User interest deleted" || "User interest not found"
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {

		userInterestService.deleteById(id);

	}
}
