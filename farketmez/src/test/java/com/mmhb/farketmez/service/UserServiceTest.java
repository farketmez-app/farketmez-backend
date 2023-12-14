package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
		User userToSave = new User("testuser", "pass123", "Test", "User", 30, 1, "40.7128", "-74.0060", "test@mail.com",
				now, null, null, new UserType());
		when(userRepository.save(any(User.class))).thenReturn(userToSave);

		User actual = userService.createUser(userToSave);

		assertNotNull(actual);
		assertEquals(userToSave.getUsername(), actual.getUsername());
		assertEquals(userToSave.getMail(), actual.getMail());
	}

	@Test
	void whenRetrievingAllUsers_thenShouldReturnListOfUsers() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		List<User> users = Arrays.asList(
				new User(1L, "testuser1", "pass123", "Test1", "User1", 30, 1, "40.7128", "-74.0060", "test1@mail.com",
						now, null, null, new UserType()),
				new User(2L, "testuser2", "pass456", "Test2", "User2", 25, 2, "34.0522", "-118.2437", "test2@mail.com",
						now, null, null, new UserType()));
		when(userRepository.findAll()).thenReturn(users);

		List<User> actual = userService.getAllUsers();

		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenUserId_whenRetrievingUser_thenShouldReturnUser() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Long userId = 1L;
		User user = new User(userId, "testuser", "pass123", "Test", "User", 30, 1, "40.7128", "-74.0060",
				"test@mail.com", now, null, null, new UserType());
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		User actual = userService.getUserById(userId);

		assertNotNull(actual);
		assertEquals(userId, actual.getId());
	}

	@Test
	void givenUserDetails_whenUpdatingUser_thenShouldReturnUpdatedUser() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Long userId = 1L;
		User userToUpdate = new User(userId, "updateduser", "pass123", "Updated", "User", 35, 1, "34.0522", "-118.2437",
				"update@mail.com", null, now, null, new UserType());
		when(userRepository.existsById(userId)).thenReturn(true);
		when(userRepository.save(any(User.class))).thenReturn(userToUpdate);

		User actual = userService.updateUser(userToUpdate);

		assertNotNull(actual);
		assertEquals(userToUpdate.getUsername(), actual.getUsername());
		assertEquals(userToUpdate.getMail(), actual.getMail());
	}

	@Test
	void givenUserId_whenDeletingUser_thenShouldPerformDeletion() {
		Long userId = 1L;
		doNothing().when(userRepository).deleteById(userId);
		userService.deleteUser(userId);
		verify(userRepository).deleteById(userId);
	}
}
