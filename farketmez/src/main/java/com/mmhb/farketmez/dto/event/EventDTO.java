package com.mmhb.farketmez.dto.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO extends EventUpdateDTO {
    Date deletedAt;
}
