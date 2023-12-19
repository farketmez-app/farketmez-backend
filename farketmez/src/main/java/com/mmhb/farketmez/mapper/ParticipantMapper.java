package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.dto.ParticipantDTO;
import com.mmhb.farketmez.dto.UserDTO;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.model.User;

public class ParticipantMapper {
	private ParticipantMapper() {
	}

	public static ParticipantDTO toParticipantDTO(Participant participant) {
		return new ParticipantDTO(participant.getId(), participant.getRating(), participant.getComment(),
				participant.getUser(), participant.getEvent());
	}

	public static Participant fromParticipantDTO(ParticipantDTO participantDTO) {
		return new Participant(participantDTO.getId(), participantDTO.getUser(), participantDTO.getEvent(),
				participantDTO.getRating(), participantDTO.getComment());
	}
}
