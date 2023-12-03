package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.mmhb.farketmez.repository.EventRepository;

class EventServiceTest {

	@Mock
	private EventRepository eventRepository;

	private EventService eventService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		eventService = new EventService(eventRepository);
	}

	@Test
	void whenCreateEvent_thenShouldReturnSavedEvent() {
		Timestamp now = Timestamp.from(Instant.now());
		Event event = new Event(/* initialize with test data */);
		when(eventRepository.save(any(Event.class))).thenReturn(event);

		Event result = eventService.createEvent(event);

		assertNotNull(result);
		assertEquals(event.getTitle(), result.getTitle());
		assertNotNull(result.getCreatedAt());
	}

	@Test
	void whenGetAllEvents_thenShouldReturnEventList() {
		List<Event> events = Arrays.asList(new Event(/* test data */), new Event(/* test data */));
		when(eventRepository.findAll()).thenReturn(events);

		List<Event> result = eventService.getAllEvents();

		assertNotNull(result);
		assertEquals(events.size(), result.size());
	}

	@Test
	void whenGetEventById_thenShouldReturnEvent() {
		Long eventId = 1L;
		Optional<Event> optionalEvent = Optional.of(new Event(/* test data */));
		when(eventRepository.findById(eventId)).thenReturn(optionalEvent);

		Event result = eventService.getEventById(eventId);

		assertNotNull(result);
	}

	@Test
	void whenUpdateEvent_thenShouldReturnUpdatedEvent() {
		Long eventId = 1L;
		Event event = new Event(/* initialize with test data */);
		event.setId(eventId);
		when(eventRepository.existsById(eventId)).thenReturn(true);
		when(eventRepository.save(any(Event.class))).thenReturn(event);

		Event result = eventService.updateEvent(event);

		assertNotNull(result);
		assertEquals(event.getTitle(), result.getTitle());
		assertNotNull(result.getUpdatedAt());
	}

	@Test
	void whenDeleteEvent_thenShouldPerformDeletion() {
		Long eventId = 1L;
		doNothing().when(eventRepository).deleteById(eventId);

		eventService.deleteEvent(eventId);

		verify(eventRepository).deleteById(eventId);
	}
}
