package com.mmhb.farketmez.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDTO extends EventUpdateDTO {
    Date deletedAt;
}
