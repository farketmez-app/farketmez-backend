package com.mmhb.farketmez.controller;

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

@RestController
@RequestMapping(value = "/eventtypes")
public class EventTypeController {

	private final EventTypeService eventTypeService;

	public EventTypeController(EventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}

	@GetMapping
	public Object getAllEventTypes() {
		return this.eventTypeService.getAllEventTypes();
	}

	@GetMapping(value = "/{id}")
	public EventTypeDTO getEventTypeById(@PathVariable Long id) {
		return eventTypeService.getEventTypeById(id).orElse(null);
	}

	@PostMapping
	public EventTypeDTO addEventType(@RequestBody EventTypeDTO eventTypeDto) {
		return eventTypeService.createEventType(eventTypeDto);
	}

	@PutMapping(value = "/{id}")
	public EventTypeDTO updateEventType(@PathVariable Long id, @RequestBody EventTypeDTO eventTypeDto) {
		eventTypeDto.setId(id);
		return eventTypeService.updateEventType(eventTypeDto);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteEventType(@PathVariable Long id) {
		eventTypeService.deleteEventType(id);
	}
}
