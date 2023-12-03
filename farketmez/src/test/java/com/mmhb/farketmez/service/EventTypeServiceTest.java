package com.mmhb.farketmez.service;

import static org.junit.Assert.assertTrue;
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

import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.repository.EventTypeRepository;

class EventTypeServiceTest {

	@Mock
	private EventTypeRepository eventTypeRepository;

	private EventTypeService eventTypeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		eventTypeService = new EventTypeService(eventTypeRepository);
	}

	@Test
	void whenCreatingEventType_thenShouldReturnSavedEventType() {
		EventType eventTypeToSave = new EventType(null, 1);
		when(eventTypeRepository.save(any(EventType.class))).thenReturn(eventTypeToSave);

		EventType actual = eventTypeService.createEventType(eventTypeToSave);

		assertNotNull(actual);
		assertEquals(eventTypeToSave.getType(), actual.getType());
	}

	@Test
	void whenRetrievingAllEventTypes_thenShouldReturnListOfEventTypes() {
		List<EventType> eventTypes = Arrays.asList(new EventType(1L, 1), new EventType(2L, 2));
		when(eventTypeRepository.findAll()).thenReturn(eventTypes);

		List<EventType> actual = eventTypeService.getAllEventTypes();

		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenEventTypeId_whenRetrievingEventType_thenShouldReturnEventType() {
		Long eventTypeId = 1L;
		EventType eventType = new EventType(eventTypeId, 1);
		when(eventTypeRepository.findById(eventTypeId)).thenReturn(Optional.of(eventType));

		Optional<EventType> actual = eventTypeService.getEventTypeById(eventTypeId);

		assertNotNull(actual);
		assertTrue(actual.isPresent());
		assertEquals(eventTypeId, actual.get().getId());
	}

	@Test
	void givenEventTypeDetails_whenUpdatingEventType_thenShouldReturnUpdatedEventType() {
		EventType eventTypeToUpdate = new EventType(1L, 2);
		when(eventTypeRepository.findById(eventTypeToUpdate.getId())).thenReturn(Optional.of(eventTypeToUpdate));
		when(eventTypeRepository.save(any(EventType.class))).thenReturn(eventTypeToUpdate);

		EventType actual = eventTypeService.updateEventType(eventTypeToUpdate);

		assertNotNull(actual);
		assertEquals(eventTypeToUpdate.getType(), actual.getType());
	}

	@Test
	void givenEventTypeId_whenDeletingEventType_thenShouldPerformDeletion() {
		Long eventTypeId = 1L;
		doNothing().when(eventTypeRepository).deleteById(eventTypeId);
		eventTypeService.deleteEventType(eventTypeId);
		verify(eventTypeRepository).deleteById(eventTypeId);
	}
}
