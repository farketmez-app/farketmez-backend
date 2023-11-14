package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.dto.UserDTO;
import com.mmhb.farketmez.mapper.UserMapper;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.repository.UserRepository;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		userService = new UserService(userRepository);
	}

	@Test
	void whenCreatingUser_thenShouldReturnSavedUser() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		UserDTO userDTOToSave = new UserDTO(null, "testuser", "pass123", "Test", "User", 30, 1, "40.7128", "-74.0060",
				new UserType(), now, now, null);
		User savedUser = UserMapper.fromUserDto(userDTOToSave);
		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		UserDTO actual = userService.createUser(userDTOToSave);

		assertNotNull(actual);
		assertEquals(userDTOToSave.getUsername(), actual.getUsername());
	}

	@Test
	void whenRetrievingAllUsers_thenShouldReturnListOfUsers() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		List<User> users = Arrays.asList(
				new User(1L, "testuser1", "pass123", "Test1", "User1", 30, 1, "40.7128", "-74.0060", now, now, null,
						new UserType()),
				new User(2L, "testuser2", "pass456", "Test2", "User2", 25, 2, "34.0522", "-118.2437", now, now, null,
						new UserType()));
		when(userRepository.findAll()).thenReturn(users);

		List<UserDTO> userDTOs = userService.getAllUsers();

		assertNotNull(userDTOs);
		assertEquals(2, userDTOs.size());
	}

	@Test
	void givenUserId_whenRetrievingUser_thenShouldReturnUser() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Long userId = 1L;
		Optional<User> user = Optional.of(new User(userId, "testuser", "pass123", "Test", "User", 30, 1, "40.7128",
				"-74.0060", now, now, null, new UserType()));
		when(userRepository.findById(userId)).thenReturn(user);

		UserDTO actual = userService.getUserById(userId);

		assertNotNull(actual);
		assertEquals(userId, actual.getId());
	}

	@Test
	void givenUserDetails_whenUpdatingUser_thenShouldReturnUpdatedUser() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		UserDTO userDTOToUpdate = new UserDTO(1L, "updateduser", "pass123", "Updated", "User", 35, 1, "34.0522",
				"-118.2437", new UserType(), now, now, null);
		User updatedUser = UserMapper.fromUserDto(userDTOToUpdate);
		when(userRepository.existsById(any(Long.class))).thenReturn(true);
		when(userRepository.save(any(User.class))).thenReturn(updatedUser);

		UserDTO actual = userService.updateUser(userDTOToUpdate);

		assertNotNull(actual);
		assertEquals(userDTOToUpdate.getUsername(), actual.getUsername());
	}

	@Test
	void givenUserId_whenDeletingUser_thenShouldPerformDeletion() {
		Long userId = 1L;
		doNothing().when(userRepository).deleteById(userId);
		userService.deleteUser(userId);
		verify(userRepository).deleteById(userId);
	}

	@Test
	void givenNonExistentUserId_whenRetrievingUser_thenShouldReturnNull() {
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		UserDTO actual = userService.getUserById(userId);

		assertNull(actual);
	}
}
