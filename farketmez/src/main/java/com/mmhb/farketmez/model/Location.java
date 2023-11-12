package com.mmhb.farketmez.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String longitude;
	private String latitude;

	public Location(Long id, String longitude, String latitude) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
