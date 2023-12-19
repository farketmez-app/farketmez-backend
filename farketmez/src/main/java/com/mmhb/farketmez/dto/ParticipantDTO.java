package com.mmhb.farketmez.dto;

import java.math.BigDecimal;

import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParticipantDTO {
	private Long id;
	private BigDecimal rating;
	private String comment;

	private User user;
	private Event event;
}
