package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.mapper.EventMapper;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;

	public EventDTO createEvent(EventDTO eventDto) {
		Event event = EventMapper.fromEventDto(eventDto);
		event = eventRepository.save(event);
		return EventMapper.toEventDto(event);
	}

	public List<EventDTO> getAllEvents() {
		return eventRepository.findAll().stream().map(EventMapper::toEventDto).collect(Collectors.toList());
	}

	public Optional<EventDTO> getEventById(Long id) {
		return eventRepository.findById(id).map(EventMapper::toEventDto);
	}

	public EventDTO updateEvent(EventDTO eventDto) {
		if (eventRepository.existsById(eventDto.getId())) {
			Event event = EventMapper.fromEventDto(eventDto);
			event = eventRepository.save(event);
			return EventMapper.toEventDto(event);
		}
		return null;
	}

	public void deleteEvent(Long id) {
		eventRepository.deleteById(id);
	}
}
