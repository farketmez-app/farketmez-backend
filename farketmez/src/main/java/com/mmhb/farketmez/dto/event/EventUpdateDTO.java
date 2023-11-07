package com.mmhb.farketmez.dto.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateDTO extends EventCreateDTO {
    Long id;
    Date updatedAt;
}
