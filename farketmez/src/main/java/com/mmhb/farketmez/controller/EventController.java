package com.mmhb.farketmez.controller;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
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
import com.mmhb.farketmez.exception.OperationNotAllowedException;
import com.mmhb.farketmez.mapper.EventMapper;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.service.EventService;
import com.mmhb.farketmez.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/events")
@CrossOrigin(origins = "http://localhost:3000")
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
	public ResponseEntity<Map<AbstractMap.SimpleEntry<Double, Double>, List<EventDTO>>> getGroupedNearEvents(
			@RequestParam(name = "lat") Double latitude, @RequestParam(name = "long") Double longitude) {
		List<Event> events = eventService.getNearEvents(latitude, longitude);
		if (events != null) {
			Map<AbstractMap.SimpleEntry<Double, Double>, List<EventDTO>> groupedEvents = events.stream()
					.collect(Collectors
							.groupingBy(event -> new AbstractMap.SimpleEntry<>(event.getLocation().getLatitude(),
									event.getLocation().getLongitude())))
					.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
							e -> e.getValue().stream().map(EventMapper::toEventDto).collect(Collectors.toList())));

			if (!groupedEvents.isEmpty()) {
				return new ResponseEntity<>(groupedEvents, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/suggestedevent/{userId}")
	public ResponseEntity<EventDTO> getSuggestEvent(@PathVariable Long userId) {
		try {
			Event event = eventService.getSuggestedEvent(userId);
			EventDTO eventDTO = EventMapper.toEventDto(event);
			return new ResponseEntity<>(eventDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/past-events")
	public ResponseEntity<List<EventDTO>> getPastEvents() {
		List<EventDTO> pastEvents = eventService.getPastEvents().stream().map(EventMapper::toEventDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(pastEvents, HttpStatus.OK);
	}

	@GetMapping("/upcoming-events")
	public ResponseEntity<List<EventDTO>> getUpcomingEvents() {
		List<EventDTO> upcomingEvents = eventService.getUpcomingEvents().stream().map(EventMapper::toEventDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(upcomingEvents, HttpStatus.OK);
	}

	// Request Body:
	// http://localhost:8080/events/join?userId=1&eventId=4
	@PostMapping("/join")
	public ResponseEntity<String> joinEvent(@RequestParam(name = "userId") Long userId,
			@RequestParam(name = "eventId") Long eventId) {
		try {
			eventService.joinEvent(userId, eventId);
			return new ResponseEntity<>("User successfully joined the event", HttpStatus.CREATED);
		} catch (OperationNotAllowedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/private/join/{accessKey}")
	public ResponseEntity<EventDTO> joinPrivateEvent(@PathVariable String accessKey,
			@RequestParam(name = "id") Long userId) {
		try {
			Event event = eventService.joinPrivateEvent(accessKey, userId);
			EventDTO eventDTO = EventMapper.toEventDto(event);
			return new ResponseEntity<>(eventDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	// Usage:
	// http://localhost:8080/events/suggestedevent-withparams?place=home&cost=cheap&date=15.01.2024&time=&pool=my-events&id=1
	@GetMapping("/suggestedevent-withparams")
	public ResponseEntity<EventDTO> findEvent(@RequestParam(name = "place") String place,
			@RequestParam(name = "cost") String cost, @RequestParam(name = "date") String date,
			@RequestParam(name = "time") String time, @RequestParam(name = "pool") List<String> pool,
			@RequestParam(name = "id") Long userId) {

		try {
			Event event = eventService.findEventWithParams(place, cost, date, time, pool, userId);
			EventDTO eventDTO = EventMapper.toEventDto(event);
			return new ResponseEntity<>(eventDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

//Request Body:http://localhost:8080/events/search?query={}
	@GetMapping("/search")
	public ResponseEntity<List<EventDTO>> searchEvents(@RequestParam String query) {
		try {
			List<Event> events = eventService.searchEvents(query);
			if (events.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			List<EventDTO> eventDTOS = events.stream().map(EventMapper::toEventDto).collect(Collectors.toList());
			return new ResponseEntity<>(eventDTOS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/filter")
	public ResponseEntity<List<EventDTO>> getFilteredEvents(@RequestParam(required = false) String cost,
			@RequestParam(required = false) String place, @RequestParam(required = false) String priority) {

		try {
			List<Event> filteredEvents = eventService.getFilteredEvents(cost, place, priority);
			List<EventDTO> eventDTOS = filteredEvents.stream().map(EventMapper::toEventDto)
					.collect(Collectors.toList());
			return new ResponseEntity<>(eventDTOS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
