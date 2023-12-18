package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.model.Event;

public class EventMapper {
	private EventMapper() {

	}

	public static EventDTO toEventDto(Event event) {
		return new EventDTO(event.getId(), event.getIsActive(), event.getTitle(), event.getDescription(),
				event.getAverageRating(), event.getDate(), event.getCreatedAt(), event.getDeletedAt(),
				event.getUpdatedAt(), event.getEventType(), event.getLocation(), event.getCreatorId());
	}

	public static Event fromEventDto(EventDTO eventDto) {
		return new Event(eventDto.getId(), eventDto.getEventType(), eventDto.getLocation(), eventDto.getCreatorId(),
				eventDto.getIsActive(), eventDto.getTitle(), eventDto.getDescription(), eventDto.getDate(),
				eventDto.getAverageRating());
	}
}
