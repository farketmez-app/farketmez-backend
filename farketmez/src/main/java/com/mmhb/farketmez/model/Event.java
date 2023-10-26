package com.mmhb.farketmez.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "events")
@Data
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "creator_id")
	private Long creatorId;

	@Column(name = "is_active", length = 1)
	private char isActive;

	@Column(length = 50)
	private String title;

	@Column(length = 200)
	private String description;

	@Column
	private Timestamp date;

	@Column(name = "average_rating", precision = 1, scale = 1)
	private BigDecimal averageRating;

	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "deleted_at")
	private Timestamp deletedAt;

	@ManyToOne
	@JoinColumn(name = "event_type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_event_event_type"))
	private EventType eventType;

	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false, foreignKey = @ForeignKey(name = "fk_event_location"))
	private Location location;

	public Event(EventType eventType, Location location, Long creatorId, char isActive, String title,
			String description, Timestamp date, BigDecimal averageRating) {
		this.eventType = eventType;
		this.location = location;
		this.creatorId = creatorId;
		this.isActive = isActive;
		this.title = title;
		this.description = description;
		this.date = date;
		this.averageRating = averageRating;
	}

}
