package com.mmhb.farketmez.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.dto.RateRequestDTO;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.service.ParticipantService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/participants")
@CrossOrigin(origins = "http://localhost:3000")
public class ParticipantController {

	private final ParticipantService participantService;

	@GetMapping
	public ResponseEntity<List<Participant>> getAllParticipants() {
		List<Participant> participants = participantService.getAllParticipants();
		return new ResponseEntity<>(participants, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
		Participant participant = participantService.getParticipantById(id);
		if (participant != null) {
			return new ResponseEntity<>(participant, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/by-user-id/{userId}")
	public ResponseEntity<List<Participant>> getParticipantsByUserId(@PathVariable Long userId) {
		List<Participant> participants = participantService.getParticipantsByUserId(userId);
		if (participants.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(participants, HttpStatus.OK);
		}
	}

	@PostMapping
	public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) {
		Participant createdParticipant = participantService.createParticipant(participant);
		return new ResponseEntity<>(createdParticipant, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Participant> updateParticipant(@RequestBody Participant participant) {
		Participant updatedParticipant = participantService.updateParticipant(participant);
		if (updatedParticipant != null) {
			return new ResponseEntity<>(updatedParticipant, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
		try {
			participantService.deleteParticipant(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/rate-event")
	public ResponseEntity<?> rateEvent(@RequestBody RateRequestDTO rateRequestDTO) {
		participantService.rateEvent(rateRequestDTO);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/rate-event")
	public ResponseEntity<?> editEventRate(@RequestBody RateRequestDTO rateRequestDTO) {
		participantService.editEventRate(rateRequestDTO.getUserId(), rateRequestDTO.getEventId(),
				rateRequestDTO.getRate(), rateRequestDTO.getComment());
		return ResponseEntity.ok().build();
	}

	// Request: GET
	// http://localhost:8080/participants/attended-rated-expired-events/{userId}
	@GetMapping("/attended-rated-expired-events/{userId}")
	public ResponseEntity<List<Event>> getAttendedRatedExpiredEvents(@PathVariable Long userId) {
		List<Event> events = participantService.getAttendedRatedExpiredEvents(userId);
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	// Request: GET
	// http://localhost:8080/participants/attended-not-rated-expired-events/{userId}
	@GetMapping("/attended-not-rated-expired-events/{userId}")
	public ResponseEntity<List<Event>> getAttendedNotRatedExpiredEvents(@PathVariable Long userId) {
		List<Event> events = participantService.getAttendedNotRatedExpiredEvents(userId);
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	// Request: GET
	// http://localhost:8080/participants/attended-not-rated-not-expired-events/{userId}
	@GetMapping("/attended-not-rated-not-expired-events/{userId}")
	public ResponseEntity<List<Event>> getAttendedNotRatedNotExpiredEvents(@PathVariable Long userId) {
		List<Event> events = participantService.getAttendedNotRatedNotExpiredEvents(userId);
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

}
