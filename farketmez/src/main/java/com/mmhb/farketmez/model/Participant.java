package com.mmhb.farketmez.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "participants")
@Data
@AllArgsConstructor
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private final Long id;

	@Column(name = "user_id")
	private final Long userId;

	@Column(name = "event_id")
	private final Long eventId;

	@Column(name = "rating")
	private final BigDecimal rating;

	@Column(name = "comment")
	private final String comment;

}
