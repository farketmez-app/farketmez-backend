package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.Optional;

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
	public List<ParticipantDTO> getAllParticipants() {
		return participantService.getAllParticipants();
	}

	@GetMapping(value = "/{id}")
	public ParticipantDTO getParticipantById(@PathVariable Long id) {
		Optional<ParticipantDTO> participantDTO = participantService.getParticipantById(id);
		return participantDTO.orElse(null);
	}

	@PostMapping(value = "/create")
	public ParticipantDTO createParticipant(@RequestBody ParticipantDTO participantDTO) {
		return participantService.createParticipant(participantDTO);
	}

	@PutMapping(value = "/{id}")
	public ParticipantDTO updateParticipant(@PathVariable Long id, @RequestBody ParticipantDTO participantDTO) {
		participantDTO.setId(id);
		return participantService.updateParticipant(participantDTO);
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
