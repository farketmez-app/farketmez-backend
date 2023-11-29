package com.mmhb.farketmez.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<LocationDTO>> getAllLocations() {
		List<LocationDTO> locations = locationService.getAllLocations();
		return new ResponseEntity<>(locations, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
		return locationService.getLocationById(id).map(location -> new ResponseEntity<>(location, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
		LocationDTO createdLocation = locationService.createLocation(locationDTO);
		if (createdLocation != null) {
			return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) {
		LocationDTO updatedLocation = locationService.updateLocation(locationDTO);
		if (updatedLocation != null) {
			return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
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
