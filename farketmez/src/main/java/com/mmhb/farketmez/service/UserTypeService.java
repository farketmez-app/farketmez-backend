package com.mmhb.farketmez.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.UserTypeDTO;
import com.mmhb.farketmez.mapper.UserTypeMapper;
import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.repository.UserTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTypeService {

	private final UserTypeRepository userTypeRepository;

	public UserTypeDTO createUserType(UserTypeDTO userTypeDTO) {
		UserType userType = UserTypeMapper.fromUserTypeDto(userTypeDTO);
		UserType savedUserType = userTypeRepository.save(userType);
		return UserTypeMapper.toUserTypeDto(savedUserType);
	}

	public List<UserTypeDTO> getAllUserTypes() {
		return userTypeRepository.findAll().stream().map(UserTypeMapper::toUserTypeDto).collect(Collectors.toList());
	}

	public UserTypeDTO getUserTypeById(Long id) {
		return userTypeRepository.findById(id).map(UserTypeMapper::toUserTypeDto).orElse(null);
	}

	public UserTypeDTO updateUserType(Long id, UserTypeDTO userTypeDTO) {
		if (userTypeRepository.existsById(id)) {
			userTypeDTO.setId(id);
			UserType userType = UserTypeMapper.fromUserTypeDto(userTypeDTO);
			UserType updatedUserType = userTypeRepository.save(userType);
			return UserTypeMapper.toUserTypeDto(updatedUserType);
		}
		return null;
	}

	public void deleteUserType(Long id) {
		userTypeRepository.deleteById(id);
	}
}
