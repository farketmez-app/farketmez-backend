package com.mmhb.farketmez.dto.event;

import com.mmhb.farketmez.model.EventType;
import com.mmhb.farketmez.model.Location;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class EventCreateDTO {
    char isActive;
    String title;
    String description;
    Timestamp date;
    BigDecimal averageRating;
    Timestamp createdAt;


    EventType eventType;
    Location location;
    Long creatorId;
}
