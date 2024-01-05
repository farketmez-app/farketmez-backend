package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.exception.DatabaseOperationException;
import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.repository.InterestRepository;
import com.mmhb.farketmez.repository.UserInterestRepository;
import com.mmhb.farketmez.repository.UserRepository;
import com.mmhb.farketmez.type.InterestType;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInterestService {

	private final UserInterestRepository userInterestRepository;
	private final UserRepository userRepository;
	private final InterestRepository interestRepository;

	@Transactional
	public UserInterest createUserInterest(Long userId, InterestType interestType) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DatabaseOperationException("User not found with id: " + userId));
		Interest interest = interestRepository.findByInterestName(interestType);

		if (interest == null) {
			interest = new Interest();
			interest.setInterestName(interestType);
			interest = interestRepository.save(interest);
		}

		UserInterest userInterest = new UserInterest(user, interest);
		return userInterestRepository.save(userInterest);
	}

	public List<UserInterest> findAll() {
		return userInterestRepository.findAll();
	}

	public UserInterest findById(Long id) {
		return userInterestRepository.findById(id)
				.orElseThrow(() -> new DatabaseOperationException("UserInterest not found with id: " + id));
	}

	@Transactional
	public void updateUserInterest(Long userId, List<InterestType> selectedInterestTypes) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

		List<UserInterest> currentInterests = userInterestRepository.findByUserId(userId);
		Set<InterestType> currentInterestTypes = currentInterests.stream()
				.map(userInterest -> userInterest.getInterest().getInterestName()).collect(Collectors.toSet());

		for (InterestType interestType : selectedInterestTypes) {
			if (!currentInterestTypes.contains(interestType)) {
				Interest interest = interestRepository.findByInterestName(interestType);
				if (interest == null) {
					interest = new Interest();
					interest.setInterestName(interestType);
					interest = interestRepository.save(interest);
				}
				userInterestRepository.save(new UserInterest(user, interest));
			}
		}

		List<UserInterest> toRemove = currentInterests.stream()
				.filter(userInterest -> !selectedInterestTypes.contains(userInterest.getInterest().getInterestName()))
				.collect(Collectors.toList());

		userInterestRepository.deleteAll(toRemove);
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

}
