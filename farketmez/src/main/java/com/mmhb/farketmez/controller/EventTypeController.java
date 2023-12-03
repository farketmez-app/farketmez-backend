package com.mmhb.farketmez.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.dto.EventTypeDTO;
import com.mmhb.farketmez.service.EventTypeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/eventtypes")
public class EventTypeController {

	private final EventTypeService eventTypeService;

	@GetMapping
	public ResponseEntity<List<EventTypeDTO>> getAllEventTypes() {
		List<EventTypeDTO> eventTypes = eventTypeService.getAllEventTypes();
		return new ResponseEntity<>(eventTypes, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventTypeDTO> getEventTypeById(@PathVariable Long id) {
		return eventTypeService.getEventTypeById(id).map(eventType -> new ResponseEntity<>(eventType, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<EventTypeDTO> createEventType(@RequestBody EventTypeDTO eventTypeDto) {
		EventTypeDTO createdEventType = eventTypeService.createEventType(eventTypeDto);
		if (createdEventType != null) {
			return new ResponseEntity<>(createdEventType, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<EventTypeDTO> updateEventType(@RequestBody EventTypeDTO eventTypeDto) {
		EventTypeDTO updatedEventType = eventTypeService.updateEventType(eventTypeDto);
		if (updatedEventType != null) {
			return new ResponseEntity<>(updatedEventType, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public void deleteEventType(@PathVariable Long id) {
		eventTypeService.deleteEventType(id);
	}
}
