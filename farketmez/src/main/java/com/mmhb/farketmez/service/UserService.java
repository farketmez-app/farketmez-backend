package com.mmhb.farketmez.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mmhb.farketmez.exception.DatabaseOperationException;
import com.mmhb.farketmez.exception.OperationNotAllowedException;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.repository.UserRepository;
import com.mmhb.farketmez.repository.UserTypeRepository;
import com.mmhb.farketmez.type.UserTypeEnum;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserTypeRepository userTypeRepository;

	@Transactional
	public User createUser(User user) {
		if (user.getUsername() == null && user.getUsername().isEmpty() && user.getPassword() == null
				&& user.getPassword().isEmpty() && user.getName() == null && user.getName().isEmpty()
				&& user.getLastname() == null && user.getLastname().isEmpty() && user.getAge() == null
				&& user.getGender() == null && user.getLongitude() == null && user.getLatitude() == null
				&& user.getMail() == null && user.getMail().isEmpty()) {
			throw new OperationNotAllowedException("Missing or incorrect user information. Please fill in all fields.");
		}

		UserType userType = userTypeRepository.findByType(UserTypeEnum.USER);
		if (userType == null) {
			throw new DatabaseOperationException("User type not found.");
		}
		user.setUserType(userType);

		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		user.setCreatedAt(currentTime);
		user.setUpdatedAt(currentTime);

		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Transactional
	public User updateUser(User user) {
		Long id = user.getId();
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new DatabaseOperationException("User not found with id: " + id));

		existingUser.setUsername(user.getUsername());
		existingUser.setName(user.getName());
		existingUser.setLastname(user.getLastname());
		existingUser.setAge(user.getAge());
		existingUser.setGender(user.getGender());
		existingUser.setLongitude(user.getLongitude());
		existingUser.setLatitude(user.getLatitude());
		existingUser.setMail(user.getMail());

		if (user.getPassword() != null && !user.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			existingUser.setPassword(encodedPassword);
		}

		existingUser.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return userRepository.save(existingUser);
	}

	@Transactional
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
