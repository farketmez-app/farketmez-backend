package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.dto.LocationDTO;
import com.mmhb.farketmez.mapper.LocationMapper;
import com.mmhb.farketmez.model.Location;
import com.mmhb.farketmez.repository.LocationRepository;

class LocationServiceTest {

	@Mock
	private LocationRepository locationRepository;

	private LocationService locationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		locationService = new LocationService(locationRepository);
	}

	@Test
	void whenCreatingLocation_thenShouldReturnSavedLocation() {
		LocationDTO locationDTOToSave = new LocationDTO(null, "40.7128", "-74.0060");
		Location savedLocation = LocationMapper.fromLocationDto(locationDTOToSave);
		when(locationRepository.save(any(Location.class))).thenReturn(savedLocation);

		LocationDTO actual = locationService.createLocation(locationDTOToSave);

		assertNotNull(actual);
		assertEquals(locationDTOToSave.getLongitude(), actual.getLongitude());
		assertEquals(locationDTOToSave.getLatitude(), actual.getLatitude());
	}

	@Test
	void whenRetrievingAllLocations_thenShouldReturnListOfLocations() {
		List<Location> locations = Arrays.asList(new Location(1L, "40.7128", "-74.0060"),
				new Location(2L, "34.0522", "-118.2437"));
		when(locationRepository.findAll()).thenReturn(locations);

		List<LocationDTO> locationDTOs = locationService.getAllLocations();

		assertNotNull(locationDTOs);
		assertEquals(2, locationDTOs.size());
	}

	@Test
	void givenLocationId_whenRetrievingLocation_thenShouldReturnLocation() {
		Long locationId = 1L;
		Optional<Location> location = Optional.of(new Location(locationId, "40.7128", "-74.0060"));
		when(locationRepository.findById(locationId)).thenReturn(location);

		Optional<LocationDTO> actual = locationService.getLocationById(locationId);

		assertNotNull(actual);
		actual.ifPresent(dto -> assertEquals(locationId, dto.getId()));
	}

	@Test
	void givenLocationDetails_whenUpdatingLocation_thenShouldReturnUpdatedLocation() {
		LocationDTO locationDTOToUpdate = new LocationDTO(1L, "34.0522", "-118.2437");
		Location updatedLocation = LocationMapper.fromLocationDto(locationDTOToUpdate);
		when(locationRepository.existsById(any(Long.class))).thenReturn(true);
		when(locationRepository.save(any(Location.class))).thenReturn(updatedLocation);

		LocationDTO actual = locationService.updateLocation(locationDTOToUpdate);

		assertNotNull(actual);
		assertEquals(locationDTOToUpdate.getLongitude(), actual.getLongitude());
		assertEquals(locationDTOToUpdate.getLatitude(), actual.getLatitude());
	}

	@Test
	void givenLocationId_whenDeletingLocation_thenShouldPerformDeletion() {
		Long locationId = 1L;
		doNothing().when(locationRepository).deleteById(locationId);
		locationService.deleteLocation(locationId);
		verify(locationRepository).deleteById(locationId);
	}
}
