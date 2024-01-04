package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.mapper.EventMapper;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.service.EventService;
import com.mmhb.farketmez.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/events")
public class EventController {

	private final EventService eventService;
	private final UserService userService;

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

	// Request Body: "http://localhost:8080/events/user-events?user-id=1"
	@GetMapping("/user-events")
	public ResponseEntity<List<EventDTO>> getEventsByCreatorId(@RequestParam(name = "user-id") Long userId) {
		List<Event> events = eventService.getEventsByCreatorId(userId);
		if (!events.isEmpty()) {
			List<EventDTO> eventDTOS = events.stream().map(EventMapper::toEventDto).toList();
			return new ResponseEntity<>(eventDTOS, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Request Body:
	// "http://localhost:8080/events/public-events?cost=orta&place=dışarıda&priority=all"
	@GetMapping("/public-events")
	public ResponseEntity<List<EventDTO>> getPublicEvents(@RequestParam(name = "cost") String cost,
			@RequestParam(name = "place") String place, @RequestParam(name = "priority") String priority) {
		List<Event> events = eventService.getPublicEvents(cost, place, priority);

		if (events != null && !events.isEmpty()) {
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

	// Request Body:
	// "http://localhost:8080/events/near-events?lat=39.748366&long=30.499565"
	@GetMapping("/near-events")
	public ResponseEntity<List<EventDTO>> getNearEvents(@RequestParam(name = "lat") Double latitude,
			@RequestParam(name = "long") Double longitude) {
		List<Event> events = eventService.getNearEvents(latitude, longitude);
		if (events != null) {
			List<EventDTO> eventDTOS = events.stream().map(EventMapper::toEventDto).collect(Collectors.toList());
			if (!eventDTOS.isEmpty()) {
				return new ResponseEntity<>(eventDTOS, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
