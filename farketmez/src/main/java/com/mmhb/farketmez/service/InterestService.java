package com.mmhb.farketmez.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.repository.InterestRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterestService {

	private final InterestRepository interestRepository;

	@Transactional
	public Interest createInterest(Interest interest) {
		if (interest.getInterestName() == null || interest.getInterestName().isEmpty()) {
			throw new IllegalArgumentException("Interest name is required.");
		}

		return interestRepository.save(interest);
	}

	public List<Interest> getAllInterests() {
		return interestRepository.findAll();
	}

	public Interest getInterestById(Long id) {
		return interestRepository.findById(id).orElse(null);
	}

	@Transactional
	public Interest updateInterest(Interest interest) {
		if (interest.getId() == null || !interestRepository.existsById(interest.getId())) {
			throw new IllegalArgumentException("Interest not found with id: " + interest.getId());
		}

		if (interest.getInterestName() == null || interest.getInterestName().isEmpty()) {
			throw new IllegalArgumentException("Interest name is required for update.");
		}

		Interest existingInterest = interestRepository.findById(interest.getId())
				.orElseThrow(() -> new IllegalArgumentException("Interest not found with id: " + interest.getId()));

		existingInterest.setInterestName(interest.getInterestName());

		return interestRepository.save(existingInterest);
	}

	@Transactional
	public void deleteInterest(Long id) {
		interestRepository.deleteById(id);
	}
}
