package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.ParticipantDTO;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.Participant;

public class ParticipantMapper {
	private ParticipantMapper() {
	}

	public static ParticipantDTO toParticipantDTO(Participant participant) {
		return new ParticipantDTO(participant.getId(), participant.getUserId(), participant.getRating(),
				participant.getComment(), participant.getEvent().getId());
	}

	public static Participant fromParticipantDTO(ParticipantDTO participantDTO, Event event) {
		return new Participant(participantDTO.getId(), participantDTO.getUserId(), participantDTO.getRating(),
				participantDTO.getComment(), event);
	}
}
