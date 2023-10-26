package com.mmhb.farketmez.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.service.ParticipantService;

@RestController
@RequestMapping(value = "/participants")
public class ParticipantController {

	private final ParticipantService participantService;

	public ParticipantController(ParticipantService participantService) {
		this.participantService = participantService;
	}

	/*
	 * example GET request to http://localhost:8080/participants
	 * 
	 * response: List of all participants
	 */
	@GetMapping
	public Object getAllParticipants() {
		return this.participantService.getAllParticipants();
	}

	/*
	 * example GET request to http://localhost:8080/participants/1
	 * 
	 * response: Participant with id 1
	 */
	@GetMapping(value = "/{id}")
	public Participant getParticipantById(@PathVariable Long id) {
		return this.participantService.getParticipantById(id).orElse(null);
	}

	/**
	 * example POST request to http://localhost:8080/participants/save with body: {
	 * ...participant details... }
	 * 
	 * response: Participant object || null if not saved
	 */
	@PostMapping(value = "save")
	public Participant saveParticipant(@RequestBody Participant participant) {
		return this.participantService.createParticipant(participant);
	}

	/*
	 * example DELETE request to http://localhost:8080/participants/1
	 * 
	 * response: "Participant deleted" || "Participant not found"
	 */
	@DeleteMapping(value = "/{id}")
	public String deleteParticipant(@PathVariable Long id) {
		if (this.participantService.getParticipantById(id).isPresent()) {
			this.participantService.deleteParticipant(id);
			return "Participant deleted";
		}
		return "Participant not found";
	}
}
