package com.mmhb.farketmez.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.service.InterestService;

@RestController
@RequestMapping(value = "/interests")
public class InterestController {

	private final InterestService interestService;

	public InterestController(InterestService interestService) {
		this.interestService = interestService;
	}

	/*
	 * example GET request to http://localhost:8080/interests
	 * 
	 * response: List of all interests
	 */
	@GetMapping
	public Object getAllInterests() {
		return this.interestService.getAllInterests();
	}

	/*
	 * example GET request to http://localhost:8080/interests/1
	 * 
	 * response: Interest with id 1
	 */
	@GetMapping(value = "/{id}")
	public Interest getInterestById(@PathVariable Long id) {
		return this.interestService.getInterestById(id).orElse(null);
	}

	/**
	 * example POST request to http://localhost:8080/interests/save with body: {
	 * "interest_name": "Some Interest" }
	 * 
	 * response: Interest object || null if not saved
	 */
	@PostMapping(value = "save")
	public Interest saveInterest(@RequestBody Interest interest) {
		return this.interestService.createInterest(interest);
	}

	/*
	 * example DELETE request to http://localhost:8080/interests/1
	 * 
	 * response: "Interest deleted" || "Interest not found"
	 */
	@DeleteMapping(value = "/{id}")
	public String deleteInterest(@PathVariable Long id) {
		if (this.interestService.getInterestById(id).isPresent()) {
			this.interestService.deleteInterest(id);
			return "Interest deleted";
		}
		return "Interest not found";
	}
}
