package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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

import com.mmhb.farketmez.dto.RateRequestDTO;
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
		User testUser = new User(1L, "username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0,
				"email@example.com", new Timestamp(System.currentTimeMillis()), null, null, userType);
		EventType eventType = new EventType();
		Location location = new Location();
		Long creatorId = 1L;
		String accessKey = "accessKey123";
		Event testEvent = new Event(2L, eventType, location, creatorId, true, false, "Test Event", "ucuz", "dışarıda",
				"Description", accessKey, new Timestamp(System.currentTimeMillis()), new BigDecimal("4.5"));
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
		User testUser = new User(1L, "username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0,
				"email@example.com", new Timestamp(System.currentTimeMillis()), null, null, new UserType());
		EventType eventType = new EventType();
		Location location = new Location();
		Long creatorId = 1L;
		String accessKey = "accessKey123";
		Event testEvent = new Event(2L, eventType, location, creatorId, true, false, "Test Event", "ucuz", "dışarıda",
				"Description", accessKey, new Timestamp(System.currentTimeMillis()), new BigDecimal("4.5"));

		List<Participant> participants = Arrays.asList(
				new Participant(1L, testUser, testEvent, new BigDecimal("4.5"), "Great event!"),
				new Participant(2L, testUser, testEvent, new BigDecimal("3.5"), "Good event"));

		when(participantRepository.findAll()).thenReturn(participants);
		List<Participant> actual = participantService.getAllParticipants();
		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenParticipantId_whenRetrievingParticipant_thenShouldReturnParticipant() {
		Long participantId = 1L;
		EventType eventType = new EventType();
		Location location = new Location();
		User testUser = new User(1L, "username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0,
				"email@example.com", new Timestamp(System.currentTimeMillis()), null, null, new UserType());
		String accessKey = "accessKey123";
		Long creatorId = 1L;
		Event testEvent = new Event(2L, eventType, location, creatorId, true, false, "Test Event", "ucuz", "dışarıda",
				"Description", accessKey, new Timestamp(System.currentTimeMillis()), new BigDecimal("4.5"));

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
		User testUser = new User(1L, "username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0,
				"email@example.com", new Timestamp(System.currentTimeMillis()), null, null, new UserType());
		EventType eventType = new EventType();
		Location location = new Location();
		String accessKey = "accessKey123";
		Long creatorId = 1L;
		Event testEvent = new Event(2L, eventType, location, creatorId, true, false, "Test Event", "ucuz", "dışarıda",
				"Description", accessKey, new Timestamp(System.currentTimeMillis()), new BigDecimal("4.5"));

		Participant participantToUpdate = new Participant(participantId, testUser, testEvent, new BigDecimal("5.0"),
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
	void whenGettingAttendedRatedExpiredEvents_thenShouldReturnEvents() {
		Long userId = 1L;
		List<Event> expectedEvents = mock(List.class);
		when(participantRepository.findAttendedRatedExpiredEvents(userId)).thenReturn(expectedEvents);

		List<Event> actualEvents = participantService.getAttendedRatedExpiredEvents(userId);

		assertNotNull(actualEvents);
		assertEquals(expectedEvents, actualEvents);
		verify(participantRepository).findAttendedRatedExpiredEvents(userId);
	}

	@Test
	void whenGettingAttendedNotRatedExpiredEvents_thenShouldReturnEvents() {
		Long userId = 1L;
		List<Event> expectedEvents = mock(List.class);
		when(participantRepository.findAttendedNotRatedExpiredEvents(userId)).thenReturn(expectedEvents);

		List<Event> actualEvents = participantService.getAttendedNotRatedExpiredEvents(userId);

		assertNotNull(actualEvents);
		assertEquals(expectedEvents, actualEvents);
		verify(participantRepository).findAttendedNotRatedExpiredEvents(userId);
	}

	@Test
	void whenGettingAttendedNotRatedNotExpiredEvents_thenShouldReturnEvents() {
		Long userId = 1L;
		List<Event> expectedEvents = mock(List.class);
		when(participantRepository.findAttendedNotRatedNotExpiredEvents(userId)).thenReturn(expectedEvents);

		List<Event> actualEvents = participantService.getAttendedNotRatedNotExpiredEvents(userId);

		assertNotNull(actualEvents);
		assertEquals(expectedEvents, actualEvents);
		verify(participantRepository).findAttendedNotRatedNotExpiredEvents(userId);
	}

	@Test
	void whenRatingExistingParticipant_thenShouldUpdateRating() {
		Long userId = 1L;
		Long eventId = 2L;
		User user = new User(userId, "username", "password", "Name", "Surname", 25, "gender", 0.0, 0.0,
				"email@example.com", new Timestamp(System.currentTimeMillis()), null, null, new UserType());
		String accessKey = "accessKey123";
		Event event = new Event(eventId, new EventType(), new Location(), userId, true, false, "Test Event", "Ucuz",
				"Dışarıda", "Description", accessKey, new Timestamp(System.currentTimeMillis()), new BigDecimal("4.5"));

		Participant existingParticipant = new Participant(1L, user, event, null, null);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
		when(participantRepository.findByUserIdAndEventId(userId, eventId))
				.thenReturn(Optional.of(existingParticipant));
		when(participantRepository.save(any(Participant.class))).thenReturn(existingParticipant);

		RateRequestDTO rateRequestDTO = new RateRequestDTO(userId, eventId, new BigDecimal("4.5"), "Great event!");
		participantService.rateEvent(rateRequestDTO);

		verify(participantRepository).save(existingParticipant);
		assertEquals(new BigDecimal("4.5"), existingParticipant.getRating());
		assertEquals("Great event!", existingParticipant.getComment());
	}
}
