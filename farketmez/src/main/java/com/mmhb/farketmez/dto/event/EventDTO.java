package com.mmhb.farketmez.dto.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO extends EventUpdateDTO {
    Timestamp deletedAt;
}
