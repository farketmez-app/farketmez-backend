package com.mmhb.farketmez.dto;

import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDTO {
    Long id;
    Boolean isActive;
    String title;
    String description;
    BigDecimal averageRating;
    Timestamp date;
    Timestamp createdAt;
    Timestamp deletedAt;
    Timestamp updatedAt;

    EventType eventType;
    Location location;
    Long creatorId;
}
