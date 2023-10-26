package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.Location;
import com.mmhb.farketmez.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepository;

	public Location createLocation(Location location) {
		return locationRepository.save(location);
	}

	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}

	public Optional<Location> getLocationById(Long id) {
		return locationRepository.findById(id);
	}

	public Location updateLocation(Location location) {
		if (locationRepository.existsById(location.getId())) {
			return locationRepository.save(location);
		}
		return null;
	}

	public void deleteLocation(Long id) {
		locationRepository.deleteById(id);
	}
}
