package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.LocationDTO;
import com.mmhb.farketmez.model.Location;

public class LocationMapper {
	private LocationMapper() {
	}

	public static LocationDTO toLocationDto(Location location) {
		LocationDTO locationDTO = new LocationDTO();
		locationDTO.setId(location.getId());
		locationDTO.setLatitude(location.getLatitude());
		locationDTO.setLongitude(location.getLongitude());
		return locationDTO;
	}

	public static Location fromLocationDto(LocationDTO locationDTO) {
		Location location = new Location();
		location.setId(locationDTO.getId());
		location.setLatitude(locationDTO.getLatitude());
		location.setLongitude(locationDTO.getLongitude());
		return location;
	}
}
