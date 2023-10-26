package com.mmhb.farketmez.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.service.EventTypeService;

@RestController
@RequestMapping(value = "/eventtypes")
public class EventTypeController {

	private final EventTypeService eventTypeService;

	public EventTypeController(EventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
	}

	/*
	 * example GET request to http://localhost:8080/eventtypes
	 * 
	 * response: List of all event types
	 */
	@GetMapping
	public Object getAllEventTypes() {
		return this.eventTypeService.getAllEventTypes();
	}

	/*
	 * example GET request to http://localhost:8080/eventtypes/1
	 * 
	 * response: EventType with id 1
	 */
	@GetMapping(value = "/{id}")
	public EventType getEventTypeById(@PathVariable Long id) {
		return this.eventTypeService.getEventTypeById(id).orElse(null);
	}

	/**
	 * example POST request to http://localhost:8080/eventtypes/save with body: {
	 * "type": 1 }
	 * 
	 * response: EventType object || null if not saved
	 */
	@PostMapping(value = "save")
	public EventType saveEventType(@RequestBody EventType eventType) {
		return this.eventTypeService.createEventType(eventType);
	}

	/*
	 * example DELETE request to http://localhost:8080/eventtypes/1
	 * 
	 * response: "EventType deleted" || "EventType not found"
	 */
	@DeleteMapping(value = "/{id}")
	public String deleteEventType(@PathVariable Long id) {
		if (this.eventTypeService.getEventTypeById(id).isPresent()) {
			this.eventTypeService.deleteEventType(id);
			return "EventType deleted";
		}
		return "EventType not found";
	}
}
