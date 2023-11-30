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

import com.mmhb.farketmez.dto.ParticipantDTO;
import com.mmhb.farketmez.service.ParticipantService;

@RestController
@RequestMapping(value = "/participants")
public class ParticipantController {

	private final ParticipantService participantService;

	public ParticipantController(ParticipantService participantService) {
		this.participantService = participantService;
	}

	@GetMapping
	public ResponseEntity<List<ParticipantDTO>> getAllParticipants() {
		List<ParticipantDTO> participants = participantService.getAllParticipants();
		return new ResponseEntity<>(participants, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable Long id) {
		return participantService.getParticipantById(id)
				.map(participant -> new ResponseEntity<>(participant, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<ParticipantDTO> createParticipant(@RequestBody ParticipantDTO participantDTO) {
		ParticipantDTO createdParticipant = participantService.createParticipant(participantDTO);
		if (createdParticipant != null) {
			return new ResponseEntity<>(createdParticipant, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<ParticipantDTO> updateParticipant(@RequestBody ParticipantDTO participantDTO) {
		ParticipantDTO updatedParticipant = participantService.updateParticipant(participantDTO);
		if (updatedParticipant != null) {
			return new ResponseEntity<>(updatedParticipant, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public String deleteParticipant(@PathVariable Long id) {
		if (participantService.getParticipantById(id).isPresent()) {
			participantService.deleteParticipant(id);
			return "Participant deleted";
		}
		return "Participant not found";
	}
}
