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

@Entity
@Table(name = "participants")
@Data
@AllArgsConstructor
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "event_id")
	private Long eventId;

	@Column(name = "rating")
	private BigDecimal rating;

	@Column(name = "comment")
	private String comment;

}
