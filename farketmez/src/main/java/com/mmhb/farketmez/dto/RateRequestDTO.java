package com.mmhb.farketmez.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RateRequestDTO {
	private Long userId;
	private Long eventId;
	private BigDecimal rate;
	private String comment;
}