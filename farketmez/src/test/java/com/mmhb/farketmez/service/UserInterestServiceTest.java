package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.dto.UserInterestDTO;
import com.mmhb.farketmez.mapper.UserInterestMapper;
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
		UserInterestDTO userInterestDTOToSave = new UserInterestDTO(null, 1L, 2L);
		UserInterest savedUserInterest = UserInterestMapper.fromUserInterest(userInterestDTOToSave);
		when(userInterestRepository.save(any(UserInterest.class))).thenReturn(savedUserInterest);

		UserInterestDTO actual = userInterestService.createUserInterest(userInterestDTOToSave);

		assertNotNull(actual);
		assertEquals(userInterestDTOToSave.getUserId(), actual.getUserId());
		assertEquals(userInterestDTOToSave.getInterestId(), actual.getInterestId());
	}

	@Test
	void whenRetrievingAllUserInterests_thenShouldReturnListOfUserInterests() {
		List<UserInterest> userInterests = Arrays.asList(new UserInterest(1L, 1L, 2L), new UserInterest(2L, 2L, 3L));
		when(userInterestRepository.findAll()).thenReturn(userInterests);

		List<UserInterestDTO> userInterestDTOs = userInterestService.findAll();

		assertNotNull(userInterestDTOs);
		assertEquals(2, userInterestDTOs.size());
	}

	@Test
	void givenUserInterestId_whenRetrievingUserInterest_thenShouldReturnUserInterest() {
		Long userInterestId = 1L;
		Optional<UserInterest> userInterest = Optional.of(new UserInterest(userInterestId, 1L, 2L));
		when(userInterestRepository.findById(userInterestId)).thenReturn(userInterest);

		UserInterestDTO actual = userInterestService.findById(userInterestId);

		assertNotNull(actual);
		assertEquals(userInterestId, actual.getId());
	}

	@Test
	void givenUserInterestDetails_whenUpdatingUserInterest_thenShouldReturnUpdatedUserInterest() {
		Long userInterestId = 1L;
		UserInterestDTO userInterestDTOToUpdate = new UserInterestDTO(userInterestId, 1L, 3L);
		UserInterest updatedUserInterest = new UserInterest(userInterestId, 1L, 3L);
		when(userInterestRepository.existsById(userInterestId)).thenReturn(true);
		when(userInterestRepository.save(any(UserInterest.class))).thenReturn(updatedUserInterest);

		UserInterestDTO actual = userInterestService.updateUserInterest(userInterestId, userInterestDTOToUpdate);

		assertNotNull(actual);
		assertEquals(userInterestDTOToUpdate.getInterestId(), actual.getInterestId());
		assertEquals(userInterestId, actual.getId());
	}
}