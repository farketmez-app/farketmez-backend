package com.mmhb.farketmez.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.exception.OperationNotAllowedException;
import com.mmhb.farketmez.exception.UserInputException;
import com.mmhb.farketmez.model.Location;
import com.mmhb.farketmez.repository.LocationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepository;

	@Transactional
	public Location createLocation(Location location) {
		if (location.getLongitude() == null || location.getLatitude() == null) {
			throw new OperationNotAllowedException(
					"Missing or incorrect location information. Please fill in longitude and latitude.");
		}
		return locationRepository.save(location);
	}

	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}

	public Location getLocationById(Long id) {
		return locationRepository.findById(id).orElse(null);
	}

	@Transactional
	public Location updateLocation(Location location) {
		if (location.getId() == null || !locationRepository.existsById(location.getId())) {
			throw new UserInputException("Location not found with id: " + location.getId());
		}
		if (location.getLongitude() == null || location.getLatitude() == null) {
			throw new UserInputException(
					"Missing or incorrect location information. Please fill in longitude and latitude.");
		}
		return locationRepository.save(location);
	}

	@Transactional
	public void deleteLocation(Long id) {
		locationRepository.deleteById(id);
	}

}
