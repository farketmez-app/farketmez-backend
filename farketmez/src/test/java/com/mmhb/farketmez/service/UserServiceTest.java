package com.mmhb.farketmez.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.repository.InterestRepository;
import com.mmhb.farketmez.repository.UserRepository;
import com.mmhb.farketmez.type.InterestType;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	private UserService userService;
	@Mock
	private InterestRepository interestRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		userService = new UserService(userRepository, interestRepository, passwordEncoder);
	}

	@Test
	void whenCreateUser_thenShouldReturnSavedUser() {
		UserType userType = new UserType();
		User userToSave = new User("username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0,
				"mail@example.com", null, null, null, userType);
		String encodedPassword = "encodedPassword";
		User savedUser = new User(1L, "username", encodedPassword, "Name", "Surname", 25, "gender", 0.0, 0.0,
				"mail@example.com", null, null, null, userType);

		when(passwordEncoder.encode(userToSave.getPassword())).thenReturn(encodedPassword);
		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		User actual = userService.createUser(userToSave);

		assertNotNull(actual);
		assertEquals(savedUser.getId(), actual.getId());
		assertEquals(encodedPassword, actual.getPassword());
	}

	@Test
	void whenRetrievingAllUsers_thenShouldReturnListOfUsers() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		UserType userType = new UserType();
		List<User> users = Arrays.asList(
				new User("username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0, "mail@example.com", null,
						null, null, userType),
				new User("username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0, "mail@example.com", null,
						null, null, userType));
		when(userRepository.findAll()).thenReturn(users);

		List<User> actual = userService.getAllUsers();

		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenUserId_whenRetrievingUser_thenShouldReturnUser() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Long userId = 1L;
		UserType userType = new UserType();
		User user = new User(userId, "username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0,
				"mail@example.com", now, now, null, userType);
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		User actual = userService.getUserById(userId);

		assertNotNull(actual);
		assertEquals(userId, actual.getId());
	}

	@Test
	void givenUserDetails_whenUpdatingUser_thenShouldReturnUpdatedUser() {
		Long userId = 1L;
		UserType userType = new UserType();
		User existingUser = new User(userId, "olduser", "oldpass", "OldName", "OldSurname", 30, "gender", 40.7128,
				-74.0060, "old@mail.com", null, null, null, userType);
		String encodedPassword = "encodedPassword";
		User userToUpdate = new User(userId, "updateduser", encodedPassword, "Updated", "User", 35, "gender", 34.0522,
				-118.2437, "update@mail.com", null, null, null, userType);

		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
		when(passwordEncoder.encode("newpass")).thenReturn(encodedPassword);
		when(userRepository.save(any(User.class))).thenReturn(userToUpdate);

		User actual = userService.updateUser(userToUpdate);

		assertNotNull(actual);
		assertEquals(userToUpdate.getUsername(), actual.getUsername());
		assertEquals(userToUpdate.getMail(), actual.getMail());
		assertEquals(encodedPassword, actual.getPassword());
	}

	@Test
	void givenUserId_whenDeletingUser_thenShouldPerformDeletion() {
		Long userId = 1L;
		doNothing().when(userRepository).deleteById(userId);
		userService.deleteUser(userId);
		verify(userRepository).deleteById(userId);
	}

	@Test
	void updateUserInterestsTest() {
		Long userId = 1L;
		Set<InterestType> interestTypes = new HashSet<>();
		interestTypes.add(InterestType.CINEMA);
		interestTypes.add(InterestType.SPORTS);

		User user = new User(userId, "username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0,
				"mail@example.com", null, null, null, new UserType());

		Interest cinemaInterest = new Interest(1L, InterestType.CINEMA);
		Interest sportsInterest = new Interest(2L, InterestType.SPORTS);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(interestRepository.findByInterestName(InterestType.CINEMA)).thenReturn(cinemaInterest);
		when(interestRepository.findByInterestName(InterestType.SPORTS)).thenReturn(sportsInterest);

		userService.updateUserInterests(userId, interestTypes);

		verify(userRepository).findById(userId);
		verify(interestRepository).findByInterestName(InterestType.CINEMA);
		verify(interestRepository).findByInterestName(InterestType.SPORTS);
		verify(userRepository).save(any(User.class));

		assertEquals(2, user.getInterests().size());
		assertTrue(user.getInterests().contains(cinemaInterest));
		assertTrue(user.getInterests().contains(sportsInterest));
	}
}
