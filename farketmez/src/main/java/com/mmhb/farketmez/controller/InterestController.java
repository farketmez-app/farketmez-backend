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
	public List<InterestDTO> getAllInterests() {
		return this.interestService.getAllInterests();
	}

	@PostMapping
	public InterestDTO addInterest(@RequestBody InterestDTO interestDTO) {
		return this.interestService.createInterest(interestDTO);
	}

	@PutMapping(value = "/{id}")
	public InterestDTO updateInterest(@PathVariable Long id, @RequestBody InterestDTO interestDTO) {
		interestDTO.setId(id);
		return this.interestService.updateInterest(interestDTO);
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
