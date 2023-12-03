package com.mmhb.farketmez.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.repository.EventRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;

	@Transactional
	public Event createEvent(Event event) {
		if (event.getTitle() == null || event.getTitle().isEmpty() || event.getDescription() == null
				|| event.getDescription().isEmpty() || event.getDate() == null) {
			throw new IllegalArgumentException(
					"Missing or incorrect event information. Please fill in all required fields.");
		}

		event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		return eventRepository.save(event);
	}

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	public Event getEventById(Long id) {
		return eventRepository.findById(id).orElse(null);
	}

	@Transactional
	public Event updateEvent(Event event) {
		if (event.getId() == null || !eventRepository.existsById(event.getId())) {
			throw new IllegalArgumentException("Event not found with id: " + event.getId());
		}

		if (event.getTitle() == null || event.getTitle().isEmpty() || event.getDescription() == null
				|| event.getDescription().isEmpty() || event.getDate() == null) {
			throw new IllegalArgumentException(
					"Missing or incorrect event information. Please fill in all required fields.");
		}

		event.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return eventRepository.save(event);
	}

	@Transactional
	public void deleteEvent(Long id) {
		eventRepository.deleteById(id);
	}
}
