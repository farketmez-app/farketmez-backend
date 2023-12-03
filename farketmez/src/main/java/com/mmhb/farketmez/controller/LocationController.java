package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.mmhb.farketmez.mapper.LocationMapper;
import com.mmhb.farketmez.model.Location;
import com.mmhb.farketmez.service.LocationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/locations")
public class LocationController {

	private final LocationService locationService;

	@GetMapping
	public ResponseEntity<List<LocationDTO>> getAllLocations() {
		List<LocationDTO> locations = locationService.getAllLocations().stream().map(LocationMapper::toLocationDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(locations, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
		Location location = locationService.getLocationById(id);
		if (location != null) {
			LocationDTO locationDTO = LocationMapper.toLocationDto(location);
			return new ResponseEntity<>(locationDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
		Location location = LocationMapper.fromLocationDto(locationDTO);
		Location createdLocation = locationService.createLocation(location);
		LocationDTO createdLocationDTO = LocationMapper.toLocationDto(createdLocation);
		return new ResponseEntity<>(createdLocationDTO, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) {
		Location location = LocationMapper.fromLocationDto(locationDTO);
		Location updatedLocation = locationService.updateLocation(location);
		if (updatedLocation != null) {
			LocationDTO updatedLocationDTO = LocationMapper.toLocationDto(updatedLocation);
			return new ResponseEntity<>(updatedLocationDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
		try {
			locationService.deleteLocation(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
