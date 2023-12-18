package com.mmhb.farketmez.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "participants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "rating")
	private BigDecimal rating;

	@Column(name = "comment")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Participant(Long id, User user, Event event, BigDecimal rating, String comment) {
		this.id = id;
		this.user = user;
		this.event = event;
		this.rating = rating;
		this.comment = comment;
	}
}
