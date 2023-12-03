package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.InterestDTO;
import com.mmhb.farketmez.mapper.InterestMapper;
import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterestService {

	private final InterestRepository interestRepository;

	public InterestDTO createInterest(InterestDTO interestDTO) {
		Interest interest = InterestMapper.fromInterestDto(interestDTO);
		Interest savedInterest = interestRepository.save(interest);
		return InterestMapper.toInterestDto(savedInterest);
	}

	public List<InterestDTO> getAllInterests() {
		return interestRepository.findAll().stream().map(InterestMapper::toInterestDto).collect(Collectors.toList());
	}

	public Optional<InterestDTO> getInterestById(Long id) {
		return interestRepository.findById(id).map(InterestMapper::toInterestDto);
	}

	public InterestDTO updateInterest(InterestDTO interestDTO) {
		if (interestRepository.existsById(interestDTO.getId())) {
			Interest interest = InterestMapper.fromInterestDto(interestDTO);
			Interest updatedInterest = interestRepository.save(interest);
			return InterestMapper.toInterestDto(updatedInterest);
		}
		return null;
	}

	public void deleteInterest(Long id) {
		interestRepository.deleteById(id);
	}
}
