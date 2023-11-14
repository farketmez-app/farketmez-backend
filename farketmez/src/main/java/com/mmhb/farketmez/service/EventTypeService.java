package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.EventTypeDTO;
import com.mmhb.farketmez.mapper.EventTypeMapper;
import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.repository.EventTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventTypeService {

	private final EventTypeRepository eventTypeRepository;

	public EventTypeDTO createEventType(EventTypeDTO eventTypeDto) {
		EventType eventType = EventTypeMapper.fromEventTypeDto(eventTypeDto);
		eventType = eventTypeRepository.save(eventType);
		return EventTypeMapper.toEventTypeDto(eventType);
	}

	public List<EventTypeDTO> getAllEventTypes() {
		return eventTypeRepository.findAll().stream().map(EventTypeMapper::toEventTypeDto).collect(Collectors.toList());
	}

	public Optional<EventTypeDTO> getEventTypeById(Long id) {
		return eventTypeRepository.findById(id).map(EventTypeMapper::toEventTypeDto);
	}

	public EventTypeDTO updateEventType(EventTypeDTO eventTypeDto) {
		if (eventTypeRepository.existsById(eventTypeDto.getId())) {
			EventType eventType = EventTypeMapper.fromEventTypeDto(eventTypeDto);
			eventType = eventTypeRepository.save(eventType);
			return EventTypeMapper.toEventTypeDto(eventType);
		}
		return null;
	}

	public void deleteEventType(Long id) {
		eventTypeRepository.deleteById(id);
	}
}