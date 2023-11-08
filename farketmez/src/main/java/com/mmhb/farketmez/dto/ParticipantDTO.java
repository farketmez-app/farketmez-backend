package com.mmhb.farketmez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParticipantDTO {
    Long id;
    Long userId;
    Long eventId;
    BigDecimal rating;
    String comment;
}
