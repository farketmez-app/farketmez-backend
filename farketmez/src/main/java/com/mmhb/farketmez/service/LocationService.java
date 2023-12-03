package com.mmhb.farketmez.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.LocationDTO;
import com.mmhb.farketmez.mapper.LocationMapper;
import com.mmhb.farketmez.model.Location;
import com.mmhb.farketmez.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepository;

	public LocationDTO createLocation(LocationDTO locationDTO) {
		Location location = LocationMapper.fromLocationDto(locationDTO);
		Location savedLocation = locationRepository.save(location);
		return LocationMapper.toLocationDto(savedLocation);
	}

	public List<LocationDTO> getAllLocations() {
		return locationRepository.findAll().stream().map(LocationMapper::toLocationDto).collect(Collectors.toList());
	}

	public Optional<LocationDTO> getLocationById(Long id) {
		return locationRepository.findById(id).map(LocationMapper::toLocationDto);
	}

	public LocationDTO updateLocation(LocationDTO locationDTO) {
		if (locationRepository.existsById(locationDTO.getId())) {
			Location location = LocationMapper.fromLocationDto(locationDTO);
			Location updatedLocation = locationRepository.save(location);
			return LocationMapper.toLocationDto(updatedLocation);
		}
		return null;
	}

	public void deleteLocation(Long id) {
		locationRepository.deleteById(id);
	}
}
