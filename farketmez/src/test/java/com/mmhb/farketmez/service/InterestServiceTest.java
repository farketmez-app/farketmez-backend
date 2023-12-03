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

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.repository.InterestRepository;

class InterestServiceTest {

	@Mock
	private InterestRepository interestRepository;

	private InterestService interestService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		interestService = new InterestService(interestRepository);
	}

	@Test
	void whenCreatingInterest_thenShouldReturnSavedInterest() {
		Interest interestToSave = new Interest(null, "Programming");
		when(interestRepository.save(any(Interest.class))).thenReturn(interestToSave);

		Interest actual = interestService.createInterest(interestToSave);

		assertNotNull(actual);
		assertEquals(interestToSave.getInterestName(), actual.getInterestName());
	}

	@Test
	void whenRetrievingAllInterests_thenShouldReturnListOfInterests() {
		List<Interest> interests = Arrays.asList(new Interest(1L, "Programming"), new Interest(2L, "Music"));
		when(interestRepository.findAll()).thenReturn(interests);

		List<Interest> actual = interestService.getAllInterests();

		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenInterestId_whenRetrievingInterest_thenShouldReturnInterest() {
		Long interestId = 1L;
		Optional<Interest> interest = Optional.of(new Interest(interestId, "Programming"));
		when(interestRepository.findById(interestId)).thenReturn(interest);

		Interest actual = interestService.getInterestById(interestId);

		assertNotNull(actual);
		assertEquals(interestId, actual.getId());
	}

	@Test
	void givenInterestDetails_whenUpdatingInterest_thenShouldReturnUpdatedInterest() {
		Long interestId = 1L;
		Interest interestToUpdate = new Interest(interestId, "Gardening");
		when(interestRepository.existsById(interestId)).thenReturn(true);
		when(interestRepository.findById(interestId)).thenReturn(Optional.of(interestToUpdate));
		when(interestRepository.save(any(Interest.class))).thenReturn(interestToUpdate);

		Interest actual = interestService.updateInterest(interestToUpdate);

		assertNotNull(actual);
		assertEquals(interestToUpdate.getInterestName(), actual.getInterestName());
	}

	@Test
	void givenInterestId_whenDeletingInterest_thenShouldPerformDeletion() {
		Long interestId = 1L;
		doNothing().when(interestRepository).deleteById(interestId);
		interestService.deleteInterest(interestId);
		verify(interestRepository).deleteById(interestId);
	}
}
