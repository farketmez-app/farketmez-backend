package com.mmhb.farketmez.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.repository.UserTypeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTypeService {

	private final UserTypeRepository userTypeRepository;

	@Transactional
	public UserType createUserType(UserType userType) {
		if (userType.getType() == null || userType.getType().isEmpty()) {
			throw new IllegalArgumentException("User Type must be provided.");
		}
		return userTypeRepository.save(userType);
	}

	public List<UserType> getAllUserTypes() {
		return userTypeRepository.findAll();
	}

	public UserType getUserTypeById(Long id) {
		return userTypeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("UserType not found with id: " + id));
	}

	@Transactional
	public UserType updateUserType(UserType userType) {
		if (userType.getId() == null) {
			throw new IllegalArgumentException("User Type ID must be provided for updating.");
		}

		UserType existingUserType = userTypeRepository.findById(userType.getId())
				.orElseThrow(() -> new EntityNotFoundException("UserType not found with id: " + userType.getId()));

		if (userType.getType() == null || userType.getType().isEmpty()) {
			throw new IllegalArgumentException("User Type must be provided.");
		}

		existingUserType.setType(userType.getType());

		return userTypeRepository.save(existingUserType);
	}

	@Transactional
	public void deleteUserType(Long id) {
		userTypeRepository.deleteById(id);
	}
}
