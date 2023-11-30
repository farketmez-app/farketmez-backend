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

import com.mmhb.farketmez.dto.InterestDTO;
import com.mmhb.farketmez.service.InterestService;

@RestController
@RequestMapping(value = "/interests")
public class InterestController {

	private final InterestService interestService;

	public InterestController(InterestService interestService) {
		this.interestService = interestService;
	}

	@GetMapping
	public ResponseEntity<List<InterestDTO>> getAllInterests() {
		List<InterestDTO> interests = interestService.getAllInterests();
		return new ResponseEntity<>(interests, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InterestDTO> createInterest(@RequestBody InterestDTO interestDTO) {
		InterestDTO createdInterest = interestService.createInterest(interestDTO);
		if (createdInterest != null) {
			return new ResponseEntity<>(createdInterest, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<InterestDTO> updateInterest(@RequestBody InterestDTO interestDTO) {
		InterestDTO updatedInterest = interestService.updateInterest(interestDTO);
		if (updatedInterest != null) {
			return new ResponseEntity<>(updatedInterest, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public String deleteInterest(@PathVariable Long id) {
		if (this.interestService.getInterestById(id).isPresent()) {
			this.interestService.deleteInterest(id);
			return "Interest deleted";
		}
		return "Interest not found";
	}
}
