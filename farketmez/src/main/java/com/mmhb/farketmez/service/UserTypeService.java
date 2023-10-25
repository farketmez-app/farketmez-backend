package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.repository.UserTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTypeService {

	private final UserTypeRepository userTypeRepository;

	public UserType createUserType(UserType userType) {
		return userTypeRepository.save(userType);
	}

	public List<UserType> getAllUserTypes() {
		return userTypeRepository.findAll();
	}

	public Optional<UserType> getUserTypeById(Long id) {
		return userTypeRepository.findById(id);
	}

	public UserType updateUserType(UserType userType) {
		if (userTypeRepository.existsById(userType.getId())) {
			return userTypeRepository.save(userType);
		}
		return null;
	}

	public void deleteUserType(Long id) {
		userTypeRepository.deleteById(id);
	}
}
