package com.mmhb.farketmez.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.model.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDTO {
	private Long id;
	private Boolean isActive;
	private Boolean isPrivate;
	private String title;
	private String description;
	private BigDecimal averageRating;
	private Timestamp date;
	private Timestamp createdAt;
	private Timestamp deletedAt;
	private Timestamp updatedAt;

	private EventType eventType;
	private Location location;
	private Long creatorId;
}
