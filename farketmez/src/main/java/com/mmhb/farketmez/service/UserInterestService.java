package com.mmhb.farketmez.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.exception.DatabaseOperationException;
import com.mmhb.farketmez.exception.OperationNotAllowedException;
import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.repository.InterestRepository;
import com.mmhb.farketmez.repository.UserInterestRepository;
import com.mmhb.farketmez.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInterestService {

	private final UserInterestRepository userInterestRepository;
	private final UserRepository userRepository;
	private final InterestRepository interestRepository;

	public UserInterest createUserInterest(UserInterest userInterest) {
		if (userInterest.getUser() == null && userInterest.getInterest() == null) {
			throw new OperationNotAllowedException("Both User ID and Interest ID must be provided.");
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
		if (userInterestDetails.getId() == null && userInterestDetails.getUser() == null
				&& userInterestDetails.getInterest() == null) {
			throw new OperationNotAllowedException("ID, User ID, and Interest ID must all be provided.");
		}

		UserInterest existingUserInterest = userInterestRepository.findById(userInterestDetails.getId()).orElseThrow(
				() -> new DatabaseOperationException("UserInterest not found with id: " + userInterestDetails.getId()));

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
			throw new DatabaseOperationException("User not found with id: " + userId);
		}

		List<UserInterest> userInterests = userInterestRepository.findByUserId(userId);
		if (userInterests.isEmpty()) {
			throw new DatabaseOperationException("No interests found for user id: " + userId);
		}
		return userInterestRepository.findInterestsByUserId(userId);
	}

	public String findStrInterestsByUserId(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new DatabaseOperationException("User not found with id: " + userId);
		}
		List<UserInterest> userInterests = userInterestRepository.findByUserId(userId);
		StringBuilder returnString = new StringBuilder();

		for (int i = 0; i < userInterests.size(); i++) {
			returnString.append(userInterests.get(i).getInterest().getInterestName().toString());
			if (i < userInterests.size() - 1) {
				returnString.append(",");
			}
		}

		return returnString.toString();
	}

	public List<Interest> setInterests(Long userId, List<Long> interestIds) {
		List<UserInterest> existingUserInterests = userInterestRepository.findByUserId(userId);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

		List<UserInterest> newUserInterests = interestIds.stream().map(interestId -> {
			Interest interest = interestRepository.findById(interestId)
					.orElseThrow(() -> new RuntimeException("Interest not found with id: " + interestId));
			return new UserInterest(user, interest);
		}).collect(Collectors.toList());

		userInterestRepository.deleteAll(existingUserInterests);
		userInterestRepository.saveAll(newUserInterests);

		List<Interest> newInterests = newUserInterests.stream().map(UserInterest::getInterest)
				.collect(Collectors.toList());

		return newInterests;
	}
}
