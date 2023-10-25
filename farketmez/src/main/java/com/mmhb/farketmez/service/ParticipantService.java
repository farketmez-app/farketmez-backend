package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantService {

	private final ParticipantRepository participantRepository;

	public Participant createParticipant(Participant participant) {
		return participantRepository.save(participant);
	}

	public List<Participant> getAllParticipants() {
		return participantRepository.findAll();
	}

	public Optional<Participant> getParticipantById(Long id) {
		return participantRepository.findById(id);
	}

	public Participant updateParticipant(Participant participant) {
		if (participantRepository.existsById(participant.getId())) {
			return participantRepository.save(participant);
		}
		return null;
	}

	public void deleteParticipant(Long id) {
		participantRepository.deleteById(id);
	}
}
