package com.mmhb.farketmez.dto.participant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParticipantCreateDTO {
    Long userId;
    Long eventId;
    BigDecimal rating;
    String comment;
}
