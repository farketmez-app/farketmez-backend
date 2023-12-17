package com.mmhb.farketmez.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParticipantDTO {
	private Long id;
	private Long userId;
	private BigDecimal rating;
	private String comment;
	private Long eventId;
}
