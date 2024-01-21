package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.exception.OperationNotAllowedException;
import com.mmhb.farketmez.model.Settings;
import com.mmhb.farketmez.repository.SettingsRepository;

class SettingsServiceTest {

	@Mock
	private SettingsRepository settingsRepository;

	private SettingsService settingsService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		settingsService = new SettingsService(settingsRepository);
	}

	@Test
	void whenGettingExistingSettings_thenShouldReturnSettings() {
		String key = "sampleKey";
		Settings settings = new Settings(1L, key, "value", LocalDateTime.now(), LocalDateTime.now());
		when(settingsRepository.findByKey(key)).thenReturn(settings);

		Settings actual = settingsService.getSettings(key);

		assertNotNull(actual);
		assertEquals(settings.getValue(), actual.getValue());
	}

	@Test
	void whenUpdatingNonExistingSettings_thenShouldThrowOperationNotAllowedException() {
		String key = "nonExistingKey";
		Settings settingsToUpdate = new Settings(null, key, "newValue", null, null);
		when(settingsRepository.findByKey(key)).thenReturn(null);

		assertThrows(OperationNotAllowedException.class, () -> settingsService.updateSettings(key, settingsToUpdate));
	}

}
