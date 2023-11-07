package com.mmhb.farketmez.dto.event;

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
