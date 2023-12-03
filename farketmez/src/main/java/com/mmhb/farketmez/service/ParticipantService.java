package com.mmhb.farketmez.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.repository.ParticipantRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantService {

	private final ParticipantRepository participantRepository;

	@Transactional
	public Participant createParticipant(Participant participant) {
		if (participant.getUserId() == null || participant.getEventId() == null || participant.getRating() == null) {
			throw new IllegalArgumentException(
					"Missing required fields. User ID, Event ID, and Rating must be provided.");
		}
		// FIXME: Puanlama için rating değeri 0 ile 5 aralığında olarka kabul edilmiştir
		// ileride ihtiyaca göre değiştirilmelidir.
		if (participant.getRating().compareTo(BigDecimal.ZERO) < 0
				|| participant.getRating().compareTo(new BigDecimal("5")) > 0) {
			throw new IllegalArgumentException("Rating must be between 0 and 5.");
		}
		return participantRepository.save(participant);
	}

	public List<Participant> getAllParticipants() {
		return participantRepository.findAll();
	}

	public Participant getParticipantById(Long id) {
		return participantRepository.findById(id).orElse(null);
	}

	@Transactional
	public Participant updateParticipant(Participant participant) {
		if (participant.getId() == null || !participantRepository.existsById(participant.getId())) {
			throw new IllegalArgumentException("Participant not found with id: " + participant.getId());
		}

		if (participant.getUserId() == null || participant.getEventId() == null || participant.getRating() == null) {
			throw new IllegalArgumentException(
					"Missing required fields. User ID, Event ID, and Rating must be provided.");
		}
		// FIXME: Puanlama için rating değeri 0 ile 5 aralığında olarka kabul edilmiştir
		// ileride ihtiyaca göre değiştirilmelidir.
		if (participant.getRating().compareTo(BigDecimal.ZERO) < 0
				|| participant.getRating().compareTo(new BigDecimal("5")) > 0) {
			throw new IllegalArgumentException("Rating must be between 0 and 5.");
		}
		return participantRepository.save(participant);
	}

	@Transactional
	public void deleteParticipant(Long id) {
		participantRepository.deleteById(id);
	}

}
