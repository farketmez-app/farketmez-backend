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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.mapper.EventMapper;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.model.Location;
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
	void whenCreatingEvent_thenShouldReturnSavedEvent() {
		Timestamp currentTimestamp = Timestamp.from(Instant.now());
		EventDTO eventToSave = new EventDTO(null, true, "Sample Title", "Sample Description", BigDecimal.ONE,
				currentTimestamp, currentTimestamp, null, currentTimestamp, new EventType(), new Location(), 1L);
		Event savedEvent = EventMapper.fromEventDto(eventToSave);
		when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

		EventDTO actual = eventService.addEvent(eventToSave);

		assertNotNull(actual);
		assertEquals(eventToSave.getTitle(), actual.getTitle());
	}

	@Test
	void whenUpdatingEvent_givenEventDetails_thenShouldReturnUpdatedEvent() {
		Timestamp currentTimestamp = Timestamp.from(Instant.now());
		EventDTO eventToUpdateDto = new EventDTO(1L, true, "Updated Title", "Updated Description", BigDecimal.ONE,
				currentTimestamp, currentTimestamp, null, currentTimestamp, new EventType(), new Location(), 1L);
		Event updatedEvent = EventMapper.fromEventDto(eventToUpdateDto);
		when(eventRepository.existsById(any(Long.class))).thenReturn(true);
		when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

		EventDTO actual = eventService.updateEvent(eventToUpdateDto);

		assertNotNull(actual);
		assertEquals(eventToUpdateDto.getTitle(), actual.getTitle());
	}

	@Test
	void whenDeletingEvent_givenEventId_thenShouldPerformDeletion() {
		Long eventId = 1L;
		doNothing().when(eventRepository).deleteById(eventId);
		eventService.deleteEvent(eventId);
		verify(eventRepository).deleteById(eventId);
	}
}
