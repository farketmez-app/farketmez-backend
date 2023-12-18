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
		UserDTO userDTO = UserMapper.toUserDto(participant.getUser());
		EventDTO eventDTO = EventMapper.toEventDto(participant.getEvent());
		return new ParticipantDTO(participant.getId(), userDTO, participant.getRating(), participant.getComment(),
				eventDTO);
	}

	public static Participant fromParticipantDTO(ParticipantDTO participantDTO) {
		User user = UserMapper.fromUserDto(participantDTO.getUser());
		Event event = EventMapper.fromEventDto(participantDTO.getEvent());
		return new Participant(participantDTO.getId(), user, event, participantDTO.getRating(),
				participantDTO.getComment());
	}
}
