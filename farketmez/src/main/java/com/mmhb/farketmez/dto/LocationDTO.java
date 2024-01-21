package com.mmhb.farketmez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationDTO {
	private Long id;
	private Double latitude;
	private Double longitude;
	private String googleMapsUrl;
}
