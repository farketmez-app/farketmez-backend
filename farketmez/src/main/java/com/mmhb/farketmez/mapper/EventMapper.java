package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.model.Event;

public class EventMapper {
	private EventMapper() {

	}

	public static EventDTO toEventDto(Event event) {
		return new EventDTO(event.getId(), event.getCreatorId(), event.getIsActive(), event.getIsPrivate(), event.getTitle(), event.getCost(), event.getPlace(),
				event.getDescription(), event.getAccessKey(), event.getAverageRating(), event.getDate(), event.getCreatedAt(),
				event.getUpdatedAt(), event.getDeletedAt(), event.getEventType(), event.getLocation());
	}

	public static Event fromEventDto(EventDTO eventDto) {
		return new Event(eventDto.getId(), eventDto.getEventType(), eventDto.getLocation(), eventDto.getCreatorId(),
				eventDto.getIsActive(), eventDto.getIsPrivate(), eventDto.getTitle(), eventDto.getCost(), eventDto.getPlace(),
				eventDto.getDescription(), eventDto.getAccessKey(), eventDto.getDate(), eventDto.getAverageRating());
	}
}
