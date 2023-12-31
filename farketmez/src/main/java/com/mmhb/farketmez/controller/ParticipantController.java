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

import com.mmhb.farketmez.dto.RateRequestDTO;
import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.service.ParticipantService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/participants")
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

	@PostMapping("/events/rate")
	public ResponseEntity<?> rateEvent(@RequestBody RateRequestDTO rateRequestDTO) {
		participantService.rateEvent(rateRequestDTO.getUserId(), rateRequestDTO.getEventId(), rateRequestDTO.getRate(),
				rateRequestDTO.getComment());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/events/rate")
	public ResponseEntity<?> editEventRate(@RequestBody RateRequestDTO rateRequestDTO) {
		participantService.editEventRate(rateRequestDTO.getUserId(), rateRequestDTO.getEventId(), rateRequestDTO.getRate(),
				rateRequestDTO.getComment());
		return ResponseEntity.ok().build();
	}

}
