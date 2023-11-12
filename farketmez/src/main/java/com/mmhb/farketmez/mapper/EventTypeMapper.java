package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.EventTypeDTO;
import com.mmhb.farketmez.model.EventType;

public class EventTypeMapper {
    private EventTypeMapper() {

    }

    public static EventTypeDTO toEventTypeDto(EventType eventType) {
        return new EventTypeDTO(eventType.getId(), eventType.getType());
    }

    public static EventType fromEventTypeDto(EventTypeDTO eventTypeDTO) {
        return new EventType(eventTypeDTO.getId(), eventTypeDTO.getType());
    }

}
