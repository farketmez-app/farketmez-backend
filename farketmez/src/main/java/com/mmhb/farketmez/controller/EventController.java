package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.mapper.EventMapper;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.service.EventService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/events")
public class EventController {

	private final EventService eventService;

	@PostMapping
	public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDto) {
		Event event = EventMapper.fromEventDto(eventDto);
		Event createdEvent = eventService.createEvent(event);
		EventDTO createdEventDto = EventMapper.toEventDto(createdEvent);
		return new ResponseEntity<>(createdEventDto, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<EventDTO>> getAllEvents() {
		List<EventDTO> events = eventService.getAllEvents().stream().map(EventMapper::toEventDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
		Event event = eventService.getEventById(id);
		if (event != null) {
			EventDTO eventDto = EventMapper.toEventDto(event);
			return new ResponseEntity<>(eventDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/public")
	public ResponseEntity<List<EventDTO>> getPublicEvents(){
		List<Event> events = eventService.getPublicEvents();
		if(!events.isEmpty()){
			List<EventDTO> eventDTOS = events.stream().map(EventMapper::toEventDto).toList();
			return new ResponseEntity<>(eventDTOS, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping
	public ResponseEntity<EventDTO> updateEvent(@RequestBody EventDTO eventDto) {
		Event event = EventMapper.fromEventDto(eventDto);
		Event updatedEvent = eventService.updateEvent(event);
		if (updatedEvent != null) {
			EventDTO updatedEventDto = EventMapper.toEventDto(updatedEvent);
			return new ResponseEntity<>(updatedEventDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
		try {
			eventService.deleteEvent(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
