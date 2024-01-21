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
		Location locationToSave = new Location(null, 40.7128, -74.0060,
				"https://maps.google.com?lat=40.7128&long=-74.0060");
		when(locationRepository.save(any(Location.class))).thenReturn(locationToSave);

		Location actual = locationService.createLocation(locationToSave);

		assertNotNull(actual);
		assertEquals(locationToSave.getLongitude(), actual.getLongitude());
		assertEquals(locationToSave.getLatitude(), actual.getLatitude());
		assertEquals(locationToSave.getGoogleMapsUrl(), actual.getGoogleMapsUrl());
	}

	@Test
	void whenRetrievingAllLocations_thenShouldReturnListOfLocations() {
		List<Location> locations = Arrays.asList(
				new Location(1L, 40.7128, -74.0060, "https://maps.google.com?lat=40.7128&long=-74.0060"),
				new Location(2L, 34.0522, -118.2437, "https://maps.google.com?lat=34.0522&long=-118.2437"));
		when(locationRepository.findAll()).thenReturn(locations);

		List<Location> actual = locationService.getAllLocations();

		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenLocationId_whenRetrievingLocation_thenShouldReturnLocation() {
		Long locationId = 1L;
		Optional<Location> location = Optional
				.of(new Location(locationId, 40.7128, -74.0060, "https://maps.google.com?lat=40.7128&long=-74.0060"));
		when(locationRepository.findById(locationId)).thenReturn(location);

		Location actual = locationService.getLocationById(locationId);

		assertNotNull(actual);
		assertEquals(locationId, actual.getId());
	}

	@Test
	void givenLocationDetails_whenUpdatingLocation_thenShouldReturnUpdatedLocation() {
		Long locationId = 1L;
		Location locationToUpdate = new Location(locationId, 34.0522, -118.2437,
				"https://maps.google.com?lat=34.0522&long=-118.2437");
		when(locationRepository.existsById(locationId)).thenReturn(true);
		when(locationRepository.save(any(Location.class))).thenReturn(locationToUpdate);

		Location actual = locationService.updateLocation(locationToUpdate);

		assertNotNull(actual);
		assertEquals(locationToUpdate.getLongitude(), actual.getLongitude());
		assertEquals(locationToUpdate.getLatitude(), actual.getLatitude());
		assertEquals(locationToUpdate.getGoogleMapsUrl(), actual.getGoogleMapsUrl());
	}

	@Test
	void givenLocationId_whenDeletingLocation_thenShouldPerformDeletion() {
		Long locationId = 1L;
		doNothing().when(locationRepository).deleteById(locationId);
		locationService.deleteLocation(locationId);
		verify(locationRepository).deleteById(locationId);
	}
}
