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

import com.mmhb.farketmez.dto.InterestDTO;
import com.mmhb.farketmez.dto.UserInterestDTO;
import com.mmhb.farketmez.mapper.InterestMapper;
import com.mmhb.farketmez.mapper.UserInterestMapper;
import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.service.UserInterestService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/userinterests")
public class UserInterestController {

	private final UserInterestService userInterestService;

	@GetMapping
	public ResponseEntity<List<UserInterestDTO>> getAllUserInterests() {
		List<UserInterest> userInterests = userInterestService.findAll();
		List<UserInterestDTO> userInterestDTOs = userInterests.stream().map(UserInterestMapper::toUserInterestDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(userInterestDTOs, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserInterestDTO> getUserInterestById(@PathVariable Long id) {
		UserInterest userInterest = userInterestService.findById(id);
		if (userInterest != null) {
			UserInterestDTO userInterestDTO = UserInterestMapper.toUserInterestDto(userInterest);
			return new ResponseEntity<>(userInterestDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<UserInterestDTO> createUserInterest(@RequestBody UserInterestDTO userInterestDTO) {
		UserInterest userInterest = UserInterestMapper.fromUserInterestDto(userInterestDTO);
		UserInterest createdUserInterest = userInterestService.createUserInterest(userInterest);
		return new ResponseEntity<>(UserInterestMapper.toUserInterestDto(createdUserInterest), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<UserInterestDTO> updateUserInterest(@PathVariable Long id,
			@RequestBody UserInterestDTO userInterestDTO) {
		userInterestDTO.setId(id);
		UserInterest userInterest = UserInterestMapper.fromUserInterestDto(userInterestDTO);
		UserInterest updatedUserInterest = userInterestService.updateUserInterest(userInterest);
		if (updatedUserInterest != null) {
			return new ResponseEntity<>(UserInterestMapper.toUserInterestDto(updatedUserInterest), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserInterest(@PathVariable Long id) {
		userInterestService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{userId}/interests")
	public ResponseEntity<List<InterestDTO>> getUserInterests(@PathVariable Long userId) {
		try {
			List<Interest> interests = userInterestService.findInterestsByUserId(userId);
			List<InterestDTO> interestDTOs = interests.stream().map(InterestMapper::toInterestDto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(interestDTOs, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
