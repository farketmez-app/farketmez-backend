package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
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
import com.mmhb.farketmez.repository.EventRepository;
import com.mmhb.farketmez.repository.LocationRepository;
import com.mmhb.farketmez.repository.ParticipantRepository;
import com.mmhb.farketmez.repository.UserInterestRepository;
import com.mmhb.farketmez.repository.UserRepository;

class EventServiceTest {

	@Mock
	private EventRepository eventRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ParticipantRepository participantRepository;
	@Mock
	private UserInterestRepository userInterestRepository;
	@Mock
	private LocationRepository locationRepository;

	private EventService eventService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		eventService = new EventService(eventRepository, userRepository, participantRepository, userInterestRepository,
				locationRepository);
	}

	@Test
	void whenCreateEvent_thenShouldReturnSavedEvent() {
		EventType eventType = new EventType();
		Location location = new Location();
		Event event = new Event(null, eventType, location, 1L, true, false, "Sinema Gecesi", "ucuz", "dışarıda",
				"Sinemada film izleme etkinliği", "access123", Timestamp.from(Instant.now()), new BigDecimal("4.5"));

		when(eventRepository.save(any(Event.class))).thenReturn(event);

		Event result = eventService.createEvent(event);

		assertNotNull(result);
		assertEquals(event.getTitle(), result.getTitle());
		assertNotNull(result.getCreatedAt());
	}

	@Test
	void whenGetAllEvents_thenShouldReturnEventList() {
		EventType eventType = new EventType();
		Location location = new Location();
		List<Event> events = Arrays.asList(
				new Event(1L, eventType, location, 1L, true, false, "Sinema Gecesi", "ucuz", "dışarıda",
						"Sinemada film izleme etkinliği", "accessKey1", Timestamp.from(Instant.now()),
						new BigDecimal("4.5")),
				new Event(2L, eventType, location, 2L, true, false, "Kitap Kulübü Toplantısı", "ucuz", "dışarıda",
						"Aylık kitap kulübü toplantısı", "accessKey2", Timestamp.from(Instant.now()),
						new BigDecimal("4.8")));
		when(eventRepository.findAll()).thenReturn(events);

		List<Event> result = eventService.getAllEvents();

		assertNotNull(result);
		assertEquals(events.size(), result.size());
	}

	@Test
	void whenGetEventById_thenShouldReturnEvent() {
		Long eventId = 1L;
		EventType eventType = new EventType();
		Location location = new Location();
		Timestamp now = Timestamp.from(Instant.now());
		String accessKey = "someAccessKey";

		Optional<Event> optionalEvent = Optional
				.of(new Event(eventId, eventType, location, 1L, true, false, "Sinema Gecesi", "ucuz", "dışarıda",
						"Sinemada film izleme etkinliği", accessKey, now, new BigDecimal("4.5")));

		when(eventRepository.findById(eventId)).thenReturn(optionalEvent);

		Event result = eventService.getEventById(eventId);

		assertNotNull(result);
		assertEquals("Sinema Gecesi", result.getTitle());
	}

	@Test
	void whenUpdateEvent_thenShouldReturnUpdatedEvent() {
		Long eventId = 1L;
		EventType eventType = new EventType();
		Location location = new Location();
		Timestamp now = Timestamp.from(Instant.now());
		String accessKey = "updatedAccessKey";

		Event event = new Event(eventId, eventType, location, 1L, true, false, "Sinema Gecesi Güncellendi", "ucuz",
				"dışarıda", "Güncellenmiş film izleme etkinliği", accessKey, now, new BigDecimal("4.6"));

		when(eventRepository.existsById(eventId)).thenReturn(true);
		when(eventRepository.save(any(Event.class))).thenReturn(event);

		Event result = eventService.updateEvent(event);

		assertNotNull(result);
		assertEquals("Sinema Gecesi Güncellendi", result.getTitle());
	}

	@Test
	void whenDeleteEvent_thenShouldPerformDeletion() {
		Long eventId = 1L;
		doNothing().when(eventRepository).deleteById(eventId);

		eventService.deleteEvent(eventId);

		verify(eventRepository).deleteById(eventId);
	}
}
