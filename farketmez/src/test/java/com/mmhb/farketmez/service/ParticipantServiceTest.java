package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.dto.ParticipantDTO;
import com.mmhb.farketmez.mapper.ParticipantMapper;
import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.repository.ParticipantRepository;

class ParticipantServiceTest {

	@Mock
	private ParticipantRepository participantRepository;

	private ParticipantService participantService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		participantService = new ParticipantService(participantRepository);
	}

	@Test
	void whenCreatingParticipant_thenShouldReturnSavedParticipant() {
		ParticipantDTO participantDTOToSave = new ParticipantDTO(null, 1L, 2L, new BigDecimal("4.5"), "Great event!");
		Participant savedParticipant = ParticipantMapper.fromParticipantDTO(participantDTOToSave);
		when(participantRepository.save(any(Participant.class))).thenReturn(savedParticipant);

		ParticipantDTO actual = participantService.createParticipant(participantDTOToSave);

		assertNotNull(actual);
		assertEquals(participantDTOToSave.getRating(), actual.getRating());
		assertEquals(participantDTOToSave.getComment(), actual.getComment());
	}

	@Test
	void whenRetrievingAllParticipants_thenShouldReturnListOfParticipants() {
		List<Participant> participants = Arrays.asList(
				new Participant(1L, 1L, 2L, new BigDecimal("4.5"), "Great event!"),
				new Participant(2L, 3L, 4L, new BigDecimal("3.5"), "Good event"));
		when(participantRepository.findAll()).thenReturn(participants);

		List<ParticipantDTO> participantDTOs = participantService.getAllParticipants();

		assertNotNull(participantDTOs);
		assertEquals(2, participantDTOs.size());
	}

	@Test
	void givenParticipantId_whenRetrievingParticipant_thenShouldReturnParticipant() {
		Long participantId = 1L;
		Optional<Participant> participant = Optional
				.of(new Participant(participantId, 1L, 2L, new BigDecimal("4.5"), "Great event!"));
		when(participantRepository.findById(participantId)).thenReturn(participant);

		Optional<ParticipantDTO> actual = participantService.getParticipantById(participantId);

		assertNotNull(actual);
		actual.ifPresent(dto -> assertEquals(participantId, dto.getId()));
	}

	@Test
	void givenParticipantDetails_whenUpdatingParticipant_thenShouldReturnUpdatedParticipant() {
		ParticipantDTO participantDTOToUpdate = new ParticipantDTO(1L, 1L, 2L, new BigDecimal("5.0"),
				"Updated Comment");
		Participant updatedParticipant = ParticipantMapper.fromParticipantDTO(participantDTOToUpdate);
		when(participantRepository.existsById(any(Long.class))).thenReturn(true);
		when(participantRepository.save(any(Participant.class))).thenReturn(updatedParticipant);

		ParticipantDTO actual = participantService.updateParticipant(participantDTOToUpdate);

		assertNotNull(actual);
		assertEquals(participantDTOToUpdate.getRating(), actual.getRating());
		assertEquals(participantDTOToUpdate.getComment(), actual.getComment());
	}

	@Test
	void givenParticipantId_whenDeletingParticipant_thenShouldPerformDeletion() {
		Long participantId = 1L;
		doNothing().when(participantRepository).deleteById(participantId);
		participantService.deleteParticipant(participantId);
		verify(participantRepository).deleteById(participantId);
	}
}
