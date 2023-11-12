package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.ParticipantDTO;
import com.mmhb.farketmez.model.Participant;

public class ParticipantMapper {
    private ParticipantMapper() {

    }

    public static ParticipantDTO toParticipantDTO(Participant participant) {
        return new ParticipantDTO(participant.getId(), participant.getUserId(), participant.getEventId(), participant.getRating(), participant.getComment());
    }

    public static Participant fromParticipantDTO(ParticipantDTO participantDTO) {
        return new Participant(participantDTO.getId(), participantDTO.getUserId(), participantDTO.getEventId(), participantDTO.getRating(), participantDTO.getComment());
    }
}
