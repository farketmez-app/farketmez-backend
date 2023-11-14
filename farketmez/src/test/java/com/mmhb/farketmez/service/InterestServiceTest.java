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

import com.mmhb.farketmez.dto.InterestDTO;
import com.mmhb.farketmez.mapper.InterestMapper;
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
	void whenCreatingLocation_thenShouldReturnSavedLocation() {
		InterestDTO interestDTOToSave = new InterestDTO(null, "Programming");
		Interest savedInterest = InterestMapper.fromInterestDto(interestDTOToSave);
		when(interestRepository.save(any(Interest.class))).thenReturn(savedInterest);

		InterestDTO actual = interestService.createInterest(interestDTOToSave);

		assertNotNull(actual);
		assertEquals(interestDTOToSave.getInterestName(), actual.getInterestName());
	}

	@Test
	void whenRetrievingAllLocations_thenShouldReturnListOfLocations() {
		List<Interest> interests = Arrays.asList(new Interest(1L, "Programming"), new Interest(2L, "Music"));
		when(interestRepository.findAll()).thenReturn(interests);

		List<InterestDTO> interestDTOs = interestService.getAllInterests();

		assertNotNull(interestDTOs);
		assertEquals(2, interestDTOs.size());
	}

	@Test
	void givenLocationId_whenRetrievingLocation_thenShouldReturnLocation() {
		Long interestId = 1L;
		Optional<Interest> interest = Optional.of(new Interest(interestId, "Programming"));
		when(interestRepository.findById(interestId)).thenReturn(interest);

		Optional<InterestDTO> actual = interestService.getInterestById(interestId);

		assertNotNull(actual);
		actual.ifPresent(dto -> assertEquals(interestId, dto.getId()));
	}

	@Test
	void givenLocationDetails_whenUpdatingLocation_thenShouldReturnUpdatedLocation() {
		InterestDTO interestDTOToUpdate = new InterestDTO(1L, "Gardening");
		Interest updatedInterest = InterestMapper.fromInterestDto(interestDTOToUpdate);
		when(interestRepository.existsById(any(Long.class))).thenReturn(true);
		when(interestRepository.save(any(Interest.class))).thenReturn(updatedInterest);

		InterestDTO actual = interestService.updateInterest(interestDTOToUpdate);

		assertNotNull(actual);
		assertEquals(interestDTOToUpdate.getInterestName(), actual.getInterestName());
	}

	@Test
	void givenLocationId_whenDeletingLocation_thenShouldPerformDeletion() {
		Long interestId = 1L;
		doNothing().when(interestRepository).deleteById(interestId);
		interestService.deleteInterest(interestId);
		verify(interestRepository).deleteById(interestId);
	}
}
