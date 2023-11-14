package com.mmhb.farketmez.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.UserDTO;
import com.mmhb.farketmez.mapper.UserMapper;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public UserDTO createUser(UserDTO userDTO) {
		User user = UserMapper.fromUserDto(userDTO);
		User savedUser = userRepository.save(user);
		return UserMapper.toUserDto(savedUser);
	}

	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
	}

	public UserDTO getUserById(Long id) {
		return userRepository.findById(id).map(UserMapper::toUserDto).orElse(null);
	}

	public UserDTO updateUser(UserDTO userDTO) {
		if (userRepository.existsById(userDTO.getId())) {
			User user = UserMapper.fromUserDto(userDTO);
			User updatedUser = userRepository.save(user);
			return UserMapper.toUserDto(updatedUser);
		}
		return null;
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
