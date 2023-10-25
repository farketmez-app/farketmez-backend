package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;

	public Event createEvent(Event event) {
		return eventRepository.save(event);
	}

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	public Optional<Event> getEventById(Long id) {
		return eventRepository.findById(id);
	}

	public Event updateEvent(Event event) {
		if (eventRepository.existsById(event.getId())) {
			return eventRepository.save(event);
		}
		return null;
	}

	public void deleteEvent(Long id) {
		eventRepository.deleteById(id);
	}
}
