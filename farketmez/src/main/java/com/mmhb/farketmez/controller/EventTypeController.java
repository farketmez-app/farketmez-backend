package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mmhb.farketmez.dto.EventTypeDTO;
import com.mmhb.farketmez.mapper.EventTypeMapper;
import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.service.EventTypeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/eventtypes")
@CrossOrigin(origins = "http://localhost:3000")
public class EventTypeController {

	private final EventTypeService eventTypeService;

	@GetMapping
	public ResponseEntity<List<EventTypeDTO>> getAllEventTypes() {
		List<EventType> eventTypes = eventTypeService.getAllEventTypes();
		List<EventTypeDTO> eventTypeDTOs = eventTypes.stream().map(EventTypeMapper::toEventTypeDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(eventTypeDTOs, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventTypeDTO> getEventTypeById(@PathVariable Long id) {
		return eventTypeService.getEventTypeById(id).map(EventTypeMapper::toEventTypeDto)
				.map(eventTypeDTO -> new ResponseEntity<>(eventTypeDTO, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<EventTypeDTO> createEventType(@RequestBody EventTypeDTO eventTypeDTO) {
		EventType eventType = EventTypeMapper.fromEventTypeDto(eventTypeDTO);
		EventType createdEventType = eventTypeService.createEventType(eventType);
		return new ResponseEntity<>(EventTypeMapper.toEventTypeDto(createdEventType), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<EventTypeDTO> updateEventType(@RequestBody EventTypeDTO eventTypeDTO) {
		EventType eventType = EventTypeMapper.fromEventTypeDto(eventTypeDTO);
		EventType updatedEventType = eventTypeService.updateEventType(eventType);
		if (updatedEventType != null) {
			return new ResponseEntity<>(EventTypeMapper.toEventTypeDto(updatedEventType), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEventType(@PathVariable Long id) {
		eventTypeService.deleteEventType(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
