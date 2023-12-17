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
import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.model.Location;
import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.repository.EventRepository;
import com.mmhb.farketmez.repository.ParticipantRepository;
import com.mmhb.farketmez.repository.UserRepository;

class ParticipantServiceTest {

	@Mock
	private ParticipantRepository participantRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private EventRepository eventRepository;

	private ParticipantService participantService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		participantService = new ParticipantService(participantRepository, userRepository, eventRepository);
	}

	@Test
	void whenCreatingParticipant_thenShouldReturnSavedParticipant() {
		UserType userType = new UserType();
		User testUser = new User(1L, "username", "password", "Name", "Surname", 25, 1, "longitude", "latitude",
				"email@example.com", new Timestamp(System.currentTimeMillis()), null, null, userType);
		EventType eventType = new EventType();
		Location location = new Location();
		Long creatorId = 1L;
		Event testEvent = new Event(2L, eventType, location, creatorId, true, "Test Event", "Description",
				new Timestamp(System.currentTimeMillis()), new BigDecimal("4.5"));
		when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
		when(eventRepository.findById(2L)).thenReturn(Optional.of(testEvent));
		Participant participantToSave = new Participant(null, testUser, testEvent, new BigDecimal("4.5"),
				"Great event!");
		when(participantRepository.save(any(Participant.class))).thenReturn(participantToSave);

		Participant actual = participantService.createParticipant(participantToSave);

		assertNotNull(actual);
		assertEquals(participantToSave.getRating(), actual.getRating());
		assertEquals(participantToSave.getComment(), actual.getComment());
	}

	@Test
	void whenRetrievingAllParticipants_thenShouldReturnListOfParticipants() {
		User testUser = new User(1L, "username", "password", "Name", "Surname", 25, 1, "longitude", "latitude",
				"email@example.com", null, null, null, new UserType());
		Event event = new Event(1L, new EventType(), new Location(), 1L, true, "Event Title", "Description",
				new Timestamp(System.currentTimeMillis()), new BigDecimal("4.5"));

		List<Participant> participants = Arrays.asList(
				new Participant(1L, testUser, event, new BigDecimal("4.5"), "Great event!"),
				new Participant(2L, testUser, event, new BigDecimal("3.5"), "Good event"));

		when(participantRepository.findAll()).thenReturn(participants);
		List<Participant> actual = participantService.getAllParticipants();
		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenParticipantId_whenRetrievingParticipant_thenShouldReturnParticipant() {
		Long participantId = 1L;
		User testUser = new User(1L, "username", "password", "Name", "Surname", 25, 1, "longitude", "latitude",
				"email@example.com", null, null, null, new UserType());
		Event testEvent = new Event(2L, new EventType(), new Location(), 1L, true, "Test Event", "Description",
				new Timestamp(System.currentTimeMillis()), new BigDecimal("4.5"));

		Participant participantToFind = new Participant(participantId, testUser, testEvent, new BigDecimal("4.5"),
				"Great event!");
		when(participantRepository.findById(participantId)).thenReturn(Optional.of(participantToFind));

		Participant actual = participantService.getParticipantById(participantId);

		assertNotNull(actual);
		assertEquals(participantToFind.getId(), actual.getId());
	}

	@Test
	void givenParticipantDetails_whenUpdatingParticipant_thenShouldReturnUpdatedParticipant() {
		Long participantId = 1L;
		User testUser = new User(1L, "username", "password", "Name", "Surname", 25, 1, "longitude", "latitude",
				"email@example.com", null, null, null, new UserType());
		Event event = new Event(2L, new EventType(), new Location(), 1L, true, "Updated Event", "Updated Description",
				new Timestamp(System.currentTimeMillis()), new BigDecimal("5.0"));

		Participant participantToUpdate = new Participant(participantId, testUser, event, new BigDecimal("5.0"),
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
		Event event1 = new Event(1L, true, "Sinemaya Gitmek", "Film izlemek için sinemaya gitmek.", now,
				new BigDecimal("4.2"));
		Event event2 = new Event(2L, true, "Kitap Okuma Kulübü", "Kitap okuma etkinliği.", now, new BigDecimal("4.8"));

		List<Event> expectedEvents = Arrays.asList(event1, event2);
		when(participantRepository.findEventsByUserId(userId)).thenReturn(expectedEvents);

		List<Event> actualEvents = participantService.getEventsByUser(userId);

		assertEquals(expectedEvents.size(), actualEvents.size());
		assertEquals(expectedEvents, actualEvents);
	}
}
