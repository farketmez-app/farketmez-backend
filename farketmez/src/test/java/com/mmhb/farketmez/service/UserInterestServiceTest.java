package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.repository.UserInterestRepository;

class UserInterestServiceTest {

	@Mock
	private UserInterestRepository userInterestRepository;

	private UserInterestService userInterestService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		userInterestService = new UserInterestService(userInterestRepository);
	}

	@Test
	void whenCreatingUserInterest_thenShouldReturnSavedUserInterest() {
		UserInterest userInterestToSave = new UserInterest(null, 1L, 2L);
		when(userInterestRepository.save(any(UserInterest.class))).thenReturn(userInterestToSave);

		UserInterest actual = userInterestService.createUserInterest(userInterestToSave);

		assertNotNull(actual);
		assertEquals(userInterestToSave.getUserId(), actual.getUserId());
		assertEquals(userInterestToSave.getInterestId(), actual.getInterestId());
	}

	@Test
	void whenRetrievingAllUserInterests_thenShouldReturnListOfUserInterests() {
		List<UserInterest> userInterests = Arrays.asList(new UserInterest(1L, 1L, 2L), new UserInterest(2L, 2L, 3L));
		when(userInterestRepository.findAll()).thenReturn(userInterests);

		List<UserInterest> actual = userInterestService.findAll();

		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenUserInterestId_whenRetrievingUserInterest_thenShouldReturnUserInterest() {
		Long userInterestId = 1L;
		Optional<UserInterest> userInterest = Optional.of(new UserInterest(userInterestId, 1L, 2L));
		when(userInterestRepository.findById(userInterestId)).thenReturn(userInterest);

		UserInterest actual = userInterestService.findById(userInterestId);

		assertNotNull(actual);
		assertEquals(userInterestId, actual.getId());
	}

	@Test
	void givenUserInterestDetails_whenUpdatingUserInterest_thenShouldReturnUpdatedUserInterest() {
		Long userInterestId = 1L;
		UserInterest userInterestToUpdate = new UserInterest(userInterestId, 1L, 3L);
		when(userInterestRepository.findById(userInterestId)).thenReturn(Optional.of(userInterestToUpdate));
		when(userInterestRepository.save(any(UserInterest.class))).thenReturn(userInterestToUpdate);

		UserInterest actual = userInterestService.updateUserInterest(userInterestId, userInterestToUpdate);

		assertNotNull(actual);
		assertEquals(userInterestToUpdate.getInterestId(), actual.getInterestId());
		assertEquals(userInterestId, actual.getId());
	}

	@Test
	void whenDeletingUserInterest_thenShouldPerformDeletion() {
		Long userInterestId = 1L;
		doNothing().when(userInterestRepository).deleteById(userInterestId);
		userInterestService.deleteById(userInterestId);
		verify(userInterestRepository).deleteById(userInterestId);
	}
}
