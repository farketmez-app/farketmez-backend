package com.mmhb.farketmez.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.model.Location;
import com.mmhb.farketmez.service.LocationService;

@RestController
@RequestMapping(value = "/locations")
public class LocationController {

	private final LocationService locationService;

	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

	/*
	 * example GET request to http://localhost:8080/locations
	 * 
	 * response: List of all locations
	 */
	@GetMapping
	public Object getAllLocations() {
		return this.locationService.getAllLocations();
	}

	/*
	 * example GET request to http://localhost:8080/locations/1
	 * 
	 * response: Location with id 1
	 */
	@GetMapping(value = "/{id}")
	public Location getLocationById(@PathVariable Long id) {
		return this.locationService.getLocationById(id).orElse(null);
	}

	/**
	 * example POST request to http://localhost:8080/locations/save with body: {
	 * "longitude": "45.76", "latitude": "4.85" }
	 * 
	 * response: Location object || null if not saved
	 */
	@PostMapping(value = "save")
	public Location saveLocation(@RequestBody Location location) {
		return this.locationService.createLocation(location);
	}

	/*
	 * example DELETE request to http://localhost:8080/locations/1
	 * 
	 * response: "Location deleted" || "Location not found"
	 */
	@DeleteMapping(value = "/{id}")
	public String deleteLocation(@PathVariable Long id) {
		if (this.locationService.getLocationById(id).isPresent()) {
			this.locationService.deleteLocation(id);
			return "Location deleted";
		}
		return "Location not found";
	}
}
