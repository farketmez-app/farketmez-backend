package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.dto.LocationDTO;
import com.mmhb.farketmez.service.LocationService;

@RestController
@RequestMapping(value = "/locations")
public class LocationController {

	private final LocationService locationService;

	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

	@GetMapping
	public List<LocationDTO> getAllLocations() {
		return locationService.getAllLocations();
	}

	@GetMapping(value = "/{id}")
	public LocationDTO getLocationById(@PathVariable Long id) {
		Optional<LocationDTO> locationDTO = locationService.getLocationById(id);
		return locationDTO.orElse(null);
	}

	@PostMapping(value = "/create")
	public LocationDTO createLocation(@RequestBody LocationDTO locationDTO) {
		return locationService.createLocation(locationDTO);
	}

	@PutMapping(value = "/{id}")
	public LocationDTO updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
		locationDTO.setId(id);
		return locationService.updateLocation(locationDTO);
	}

	@DeleteMapping(value = "/{id}")
	public String deleteLocation(@PathVariable Long id) {
		if (locationService.getLocationById(id).isPresent()) {
			locationService.deleteLocation(id);
			return "Location deleted";
		}
		return "Location not found";
	}
}
