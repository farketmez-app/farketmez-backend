package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.repository.UserInterestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInterestService {

	private final UserInterestRepository userInterestRepository;

	public List<UserInterest> findAll() {
		return userInterestRepository.findAll();
	}

	public Optional<UserInterest> findById(Long id) {
		return userInterestRepository.findById(id);
	}

	public UserInterest save(UserInterest userInterest) {
		return userInterestRepository.save(userInterest);
	}

	public void deleteById(Long id) {
		userInterestRepository.deleteById(id);
	}
}
