package com.mmhb.farketmez.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	@Transactional
	public User createUser(User user) {
		if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null
				|| user.getPassword().isEmpty() || user.getName() == null || user.getName().isEmpty()
				|| user.getSurname() == null || user.getSurname().isEmpty() || user.getAge() == null
				|| user.getGender() == null || user.getLongitude() == null || user.getLongitude().isEmpty()
				|| user.getLatitude() == null || user.getLatitude().isEmpty() || user.getMail() == null
				|| user.getMail().isEmpty()) {
			throw new IllegalArgumentException("Missing or incorrect user information. Please fill in all fields.");
		}
		return null;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Transactional
	public User updateUser(User user) {
		if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null
				|| user.getPassword().isEmpty() || user.getName() == null || user.getName().isEmpty()
				|| user.getSurname() == null || user.getSurname().isEmpty() || user.getAge() == null
				|| user.getGender() == null || user.getLongitude() == null || user.getLongitude().isEmpty()
				|| user.getLatitude() == null || user.getLatitude().isEmpty() || user.getMail() == null
				|| user.getMail().isEmpty()) {
			throw new IllegalArgumentException("Missing or incorrect user information. Please fill in all fields.");
		}

		if (!userRepository.existsById(user.getId())) {
			throw new IllegalArgumentException("User not found with id: " + user.getId());
		}

		user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return userRepository.save(user);
	}

	@Transactional
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
