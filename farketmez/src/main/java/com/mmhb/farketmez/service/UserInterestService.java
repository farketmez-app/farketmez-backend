package com.mmhb.farketmez.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.UserInterestDTO;
import com.mmhb.farketmez.mapper.UserInterestMapper;
import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.repository.UserInterestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInterestService {

	private final UserInterestRepository userInterestRepository;

	public UserInterestDTO createUserInterest(UserInterestDTO userInterestDTO) {
		UserInterest userInterest = UserInterestMapper.fromUserInterest(userInterestDTO);
		UserInterest savedUserInterest = userInterestRepository.save(userInterest);
		return UserInterestMapper.toUserInterestDto(savedUserInterest);
	}

	public List<UserInterestDTO> findAll() {
		return userInterestRepository.findAll().stream().map(UserInterestMapper::toUserInterestDto)
				.collect(Collectors.toList());
	}

	public UserInterestDTO findById(Long id) {
		return userInterestRepository.findById(id).map(UserInterestMapper::toUserInterestDto).orElse(null);
	}

	public UserInterestDTO updateUserInterest(Long id, UserInterestDTO userInterestDTO) {
		if (userInterestRepository.existsById(id)) {
			userInterestDTO.setId(id);
			UserInterest userInterest = UserInterestMapper.fromUserInterest(userInterestDTO);
			UserInterest updatedUserInterest = userInterestRepository.save(userInterest);
			return UserInterestMapper.toUserInterestDto(updatedUserInterest);
		}
		return null;
	}

	public void deleteById(Long id) {
		userInterestRepository.deleteById(id);
	}
}
