package com.mmhb.farketmez.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "creator_id")
	private Long creatorId;

	@Column(name = "is_active", length = 1)
	private Boolean isActive;

	@Column(name = "is_private", length = 1)
	private Boolean isPrivate;

	@Column(length = 50)
	private String title;

	@Column(length = 50)
	private String cost;

	@Column(length = 50)
	private String place;

	@Column(length = 200)
	private String description;

	@Column
	private Timestamp date;

	@Column(name = "average_rating", precision = 2, scale = 1)
	private BigDecimal averageRating;

	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "deleted_at")
	private Timestamp deletedAt;

	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	private Location location;

	@ManyToOne
	@JoinColumn(name = "event_type_id", nullable = false)
	private EventType eventType;

	public Event(Long id, EventType eventType, Location location, Long creatorId, Boolean isActive, Boolean isPrivate, String title,
			String cost, String place, String description, Timestamp date, BigDecimal averageRating) {
		this.id = id;
		this.eventType = eventType;
		this.location = location;
		this.creatorId = creatorId;
		this.isActive = isActive;
		this.isPrivate = isPrivate;
		this.title = title;
		this.cost = cost;
		this.place = place;
		this.description = description;
		this.date = date;
		this.averageRating = averageRating;
	}

	public Event(Long id, Boolean isActive, String title, String description, Timestamp date,
			BigDecimal averageRating) {
		this.id = id;
		this.isActive = isActive;
		this.title = title;
		this.description = description;
		this.date = date;
		this.averageRating = averageRating;
	}

}
