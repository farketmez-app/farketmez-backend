package com.mmhb.farketmez.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.repository.UserInterestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInterestService {

	private final UserInterestRepository userInterestRepository;

	public UserInterest createUserInterest(UserInterest userInterest) {
		if (userInterest.getUserId() == null || userInterest.getInterestId() == null) {
			throw new IllegalArgumentException("Both User ID and Interest ID must be provided.");
		}
		return userInterestRepository.save(userInterest);
	}

	public List<UserInterest> findAll() {
		return userInterestRepository.findAll();
	}

	public UserInterest findById(Long id) {
		return userInterestRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("UserInterest not found with id: " + id));
	}

	public UserInterest updateUserInterest(UserInterest userInterestDetails) {
		if (userInterestDetails.getId() == null || userInterestDetails.getUserId() == null
				|| userInterestDetails.getInterestId() == null) {
			throw new IllegalArgumentException("ID, User ID, and Interest ID must all be provided.");
		}

		UserInterest existingUserInterest = userInterestRepository.findById(userInterestDetails.getId()).orElseThrow(
				() -> new EntityNotFoundException("UserInterest not found with id: " + userInterestDetails.getId()));

		existingUserInterest.setUserId(userInterestDetails.getUserId());
		existingUserInterest.setInterestId(userInterestDetails.getInterestId());

		return userInterestRepository.save(existingUserInterest);
	}

	public void deleteById(Long id) {
		userInterestRepository.deleteById(id);
	}

}
