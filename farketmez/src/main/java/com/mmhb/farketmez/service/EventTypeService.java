package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.repository.EventTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventTypeService {

	private final EventTypeRepository eventTypeRepository;

	public EventType createEventType(EventType eventType) {
		if (eventType.getType() == null || !StringUtils.hasText(eventType.getType().toString())) {
			throw new IllegalArgumentException("EventType type is required and cannot be null or empty.");
		}
		return eventTypeRepository.save(eventType);
	}

	public List<EventType> getAllEventTypes() {
		return eventTypeRepository.findAll();
	}

	public Optional<EventType> getEventTypeById(Long id) {
		return eventTypeRepository.findById(id);
	}

	public EventType updateEventType(EventType updatedEventType) {
		if (updatedEventType.getType() == null || !StringUtils.hasText(updatedEventType.getType().toString())) {
			throw new IllegalArgumentException("EventType type is required and cannot be null or empty.");
		}
		return eventTypeRepository.findById(updatedEventType.getId()).map(eventType -> {
			eventType.setType(updatedEventType.getType());
			return eventTypeRepository.save(eventType);
		}).orElse(null);
	}

	public void deleteEventType(Long id) {
		eventTypeRepository.deleteById(id);
	}
}
