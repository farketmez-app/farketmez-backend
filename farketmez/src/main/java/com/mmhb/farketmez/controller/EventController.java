package com.mmhb.farketmez.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.service.EventService;

@RestController
@RequestMapping(value = "/events")
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	/*
	 * example GET request to http://localhost:8080/events
	 * 
	 * response: List of all events
	 */
	@GetMapping
	public Object getAllEvents() {
		return this.eventService.getAllEvents();
	}

	/*
	 * example GET request to http://localhost:8080/events/1
	 * 
	 * response: Event with id 1
	 */
	@GetMapping(value = "/{id}")
	public Event getEventById(@PathVariable Long id) {
		return this.eventService.getEventById(id).orElse(null);
	}

	/**
	 * example POST request to http://localhost:8080/events/save with body: {
	 * ...event details... }
	 * 
	 * response: Event object || null if not saved
	 */
	@PostMapping(value = "save")
	public Event saveEvent(@RequestBody Event event) {
		return this.eventService.createEvent(event);
	}

	/*
	 * example DELETE request to http://localhost:8080/events/1
	 * 
	 * response: "Event deleted" || "Event not found"
	 */
	@DeleteMapping(value = "/{id}")
	public String deleteEvent(@PathVariable Long id) {
		if (this.eventService.getEventById(id).isPresent()) {
			this.eventService.deleteEvent(id);
			return "Event deleted";
		}
		return "Event not found";
	}
}
