package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mmhb.farketmez.dto.InterestDTO;
import com.mmhb.farketmez.mapper.InterestMapper;
import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.service.InterestService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/interests")
@CrossOrigin(origins = "http://localhost:3000")
public class InterestController {

	private final InterestService interestService;

	@GetMapping
	public ResponseEntity<List<InterestDTO>> getAllInterests() {
		List<InterestDTO> interests = interestService.getAllInterests().stream().map(InterestMapper::toInterestDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(interests, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InterestDTO> getInterestById(@PathVariable Long id) {
		Interest interest = interestService.getInterestById(id);
		if (interest != null) {
			InterestDTO interestDTO = InterestMapper.toInterestDto(interest);
			return new ResponseEntity<>(interestDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<InterestDTO> createInterest(@RequestBody InterestDTO interestDTO) {
		Interest interest = InterestMapper.fromInterestDto(interestDTO);
		Interest createdInterest = interestService.createInterest(interest);
		InterestDTO createdInterestDTO = InterestMapper.toInterestDto(createdInterest);
		return new ResponseEntity<>(createdInterestDTO, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<InterestDTO> updateInterest(@RequestBody InterestDTO interestDTO) {
		Interest interest = InterestMapper.fromInterestDto(interestDTO);
		Interest updatedInterest = interestService.updateInterest(interest);
		if (updatedInterest != null) {
			InterestDTO updatedInterestDTO = InterestMapper.toInterestDto(updatedInterest);
			return new ResponseEntity<>(updatedInterestDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteInterest(@PathVariable Long id) {
		try {
			interestService.deleteInterest(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
