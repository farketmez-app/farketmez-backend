package com.mmhb.farketmez.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_types")
@Data
@NoArgsConstructor
public class EventType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer type;

	public EventType(Long id, Integer type) {
		this.id = id;
		this.type = type;
	}
}