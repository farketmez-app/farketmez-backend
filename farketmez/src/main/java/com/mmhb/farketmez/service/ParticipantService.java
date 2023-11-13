package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.ParticipantDTO;
import com.mmhb.farketmez.mapper.ParticipantMapper;
import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantService {

	private final ParticipantRepository participantRepository;

	public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {
		Participant participant = ParticipantMapper.fromParticipantDTO(participantDTO);
		Participant savedParticipant = participantRepository.save(participant);
		return ParticipantMapper.toParticipantDTO(savedParticipant);
	}

	public List<ParticipantDTO> getAllParticipants() {
		return participantRepository.findAll().stream().map(ParticipantMapper::toParticipantDTO)
				.collect(Collectors.toList());
	}

	public Optional<ParticipantDTO> getParticipantById(Long id) {
		return participantRepository.findById(id).map(ParticipantMapper::toParticipantDTO);
	}

	public ParticipantDTO updateParticipant(ParticipantDTO participantDTO) {
		if (participantRepository.existsById(participantDTO.getId())) {
			Participant participant = ParticipantMapper.fromParticipantDTO(participantDTO);
			Participant updatedParticipant = participantRepository.save(participant);
			return ParticipantMapper.toParticipantDTO(updatedParticipant);
		}
		return null;
	}

	public void deleteParticipant(Long id) {
		participantRepository.deleteById(id);
	}
}
