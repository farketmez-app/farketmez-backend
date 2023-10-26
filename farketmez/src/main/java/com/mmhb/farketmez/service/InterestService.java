package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterestService {

	private final InterestRepository interestRepository;

	public Interest createInterest(Interest interest) {
		return interestRepository.save(interest);
	}

	public List<Interest> getAllInterests() {
		return interestRepository.findAll();
	}

	public Optional<Interest> getInterestById(Long id) {
		return interestRepository.findById(id);
	}

	public Interest updateInterest(Interest interest) {
		if (interestRepository.existsById(interest.getId())) {
			return interestRepository.save(interest);
		}
		return null;
	}

	public void deleteInterest(Long id) {
		interestRepository.deleteById(id);
	}
}
