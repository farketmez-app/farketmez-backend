package com.mmhb.farketmez.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.service.EventService;

@RestController
@RequestMapping(value = "/events")
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping
	public EventDTO addEvent(@RequestBody EventDTO eventDto) {
		return eventService.addEvent(eventDto);
	}

	@GetMapping
	public List<EventDTO> getAllEvents() {
		return eventService.getAllEvents();
	}

	@GetMapping(value = "/{id}")
	public EventDTO getEventById(@PathVariable Long id) {
		return eventService.getEventById(id).orElse(null);
	}

	@PutMapping
	public EventDTO updateEvent(@RequestBody EventDTO eventDto) {
		return eventService.updateEvent(eventDto);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteEvent(@PathVariable Long id) {
		eventService.deleteEvent(id);
	}
}
