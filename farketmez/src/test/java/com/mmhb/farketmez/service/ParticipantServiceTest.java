package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.model.Event;
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
		Participant participantToSave = new Participant(null, 1L, 2L, new BigDecimal("4.5"), "Great event!");
		when(participantRepository.save(any(Participant.class))).thenReturn(participantToSave);

		Participant actual = participantService.createParticipant(participantToSave);

		assertNotNull(actual);
		assertEquals(participantToSave.getRating(), actual.getRating());
		assertEquals(participantToSave.getComment(), actual.getComment());
	}

	@Test
	void whenRetrievingAllParticipants_thenShouldReturnListOfParticipants() {
		List<Participant> participants = Arrays.asList(
				new Participant(1L, 1L, 2L, new BigDecimal("4.5"), "Great event!"),
				new Participant(2L, 3L, 4L, new BigDecimal("3.5"), "Good event"));
		when(participantRepository.findAll()).thenReturn(participants);

		List<Participant> actual = participantService.getAllParticipants();

		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenParticipantId_whenRetrievingParticipant_thenShouldReturnParticipant() {
		Long participantId = 1L;
		Optional<Participant> participant = Optional
				.of(new Participant(participantId, 1L, 2L, new BigDecimal("4.5"), "Great event!"));
		when(participantRepository.findById(participantId)).thenReturn(participant);

		Participant actual = participantService.getParticipantById(participantId);

		assertNotNull(actual);
		assertEquals(participantId, actual.getId());
	}

	@Test
	void givenParticipantDetails_whenUpdatingParticipant_thenShouldReturnUpdatedParticipant() {
		Long participantId = 1L;
		Participant participantToUpdate = new Participant(participantId, 1L, 2L, new BigDecimal("5.0"),
				"Updated Comment");
		when(participantRepository.existsById(participantId)).thenReturn(true);
		when(participantRepository.save(any(Participant.class))).thenReturn(participantToUpdate);

		Participant actual = participantService.updateParticipant(participantToUpdate);

		assertNotNull(actual);
		assertEquals(participantToUpdate.getRating(), actual.getRating());
		assertEquals(participantToUpdate.getComment(), actual.getComment());
	}

	@Test
	void givenParticipantId_whenDeletingParticipant_thenShouldPerformDeletion() {
		Long participantId = 1L;
		doNothing().when(participantRepository).deleteById(participantId);
		participantService.deleteParticipant(participantId);
		verify(participantRepository).deleteById(participantId);
	}

	@Test
	void whenGetEventsByUser_thenShouldReturnListOfEvents() {
		Long userId = 1L;
		Timestamp now = new Timestamp(System.currentTimeMillis());

		Event event1 = new Event(1L, null, null, userId, true, "Sinemaya Gitmek", "Film izlemek için sinemaya gitmek.",
				now, new BigDecimal("4.2"));
		Event event2 = new Event(2L, null, null, userId, true, "Kitap Okuma Kulübü", "Kitap okuma etkinliği.", now,
				new BigDecimal("4.8"));

		List<Event> expectedEvents = Arrays.asList(event1, event2);

		when(participantRepository.findEventsByUserId(userId)).thenReturn(expectedEvents);

		List<Event> actualEvents = participantService.getEventsByUser(userId);

		assertEquals(expectedEvents.size(), actualEvents.size());
		assertEquals(expectedEvents, actualEvents);
	}
}
