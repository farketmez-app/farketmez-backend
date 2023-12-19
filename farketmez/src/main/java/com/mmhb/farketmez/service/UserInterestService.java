package com.mmhb.farketmez.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.repository.UserInterestRepository;
import com.mmhb.farketmez.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInterestService {

	private final UserInterestRepository userInterestRepository;
	private final UserRepository userRepository;

	public UserInterest createUserInterest(UserInterest userInterest) {
		if (userInterest.getUser() == null || userInterest.getInterest() == null) {
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
		if (userInterestDetails.getId() == null || userInterestDetails.getUser() == null
				|| userInterestDetails.getInterest() == null) {
			throw new IllegalArgumentException("ID, User ID, and Interest ID must all be provided.");
		}

		UserInterest existingUserInterest = userInterestRepository.findById(userInterestDetails.getId()).orElseThrow(
				() -> new EntityNotFoundException("UserInterest not found with id: " + userInterestDetails.getId()));

		existingUserInterest.setUser(userInterestDetails.getUser());
		existingUserInterest.setInterest(userInterestDetails.getInterest());

		return userInterestRepository.save(existingUserInterest);
	}

	public void deleteById(Long id) {
		userInterestRepository.deleteById(id);
	}

	public List<Interest> findInterestsByUserId(Long userId) {
		boolean isUserExists = userRepository.existsById(userId);
		if (!isUserExists) {
			throw new EntityNotFoundException("User not found with id: " + userId);
		}

		List<UserInterest> userInterests = userInterestRepository.findByUserId(userId);
		if (userInterests.isEmpty()) {
			throw new EntityNotFoundException("No interests found for user id: " + userId);
		}
		return userInterestRepository.findInterestsByUserId(userId);
	}

}
