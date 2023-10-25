package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.repository.EventTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventTypeService {

	private final EventTypeRepository eventTypeRepository;

	public EventType createEventType(EventType eventType) {
		return eventTypeRepository.save(eventType);
	}

	public List<EventType> getAllEventTypes() {
		return eventTypeRepository.findAll();
	}

	public Optional<EventType> getEventTypeById(Long id) {
		return eventTypeRepository.findById(id);
	}

	public EventType updateEventType(EventType eventType) {
		if (eventTypeRepository.existsById(eventType.getId())) {
			return eventTypeRepository.save(eventType);
		}
		return null;
	}

	public void deleteEventType(Long id) {
		eventTypeRepository.deleteById(id);
	}
}
