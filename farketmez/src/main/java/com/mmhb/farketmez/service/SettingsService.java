package com.mmhb.farketmez.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmhb.farketmez.model.Settings;
import com.mmhb.farketmez.repository.SettingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingsService {

	private final SettingsRepository settingsRepository;

	@Transactional(readOnly = true)
	public Settings getSettings(String key) throws EntityNotFoundException {
		Settings settings = settingsRepository.findByKey(key);
		if (settings == null) {
			throw new EntityNotFoundException("Setting with key " + key + " not found.");
		}
		return settings;
	}

	@Transactional
	public Settings updateSettings(String key, Settings settingsUpdate) {
		Settings settings = settingsRepository.findByKey(key);
		if (settings == null) {
			throw new EntityNotFoundException("Setting with key " + key + " not found.");
		}

		validateSettingsUpdate(settingsUpdate);

		settings.setValue(settingsUpdate.getValue());
		settings.setUpdateDate(LocalDateTime.now());
		return settingsRepository.save(settings);
	}

	private void validateSettingsUpdate(Settings settings) {
		if (settings.getValue() == null || settings.getValue().isEmpty()) {
			throw new IllegalArgumentException("Value for settings cannot be null or empty.");
		}
	}
}
