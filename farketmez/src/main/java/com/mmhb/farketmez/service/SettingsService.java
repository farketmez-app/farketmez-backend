package com.mmhb.farketmez.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmhb.farketmez.dto.SettingsDTO;
import com.mmhb.farketmez.mapper.SettingsMapper;
import com.mmhb.farketmez.model.Settings;
import com.mmhb.farketmez.repository.SettingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingsService {

	private final SettingsRepository settingsRepository;

	@Transactional(readOnly = true)
	public SettingsDTO getSettings(String key) throws EntityNotFoundException {
		Settings settings = settingsRepository.findByKey(key);
		if (settings == null) {
			throw new EntityNotFoundException("Setting with key " + key + " not found.");
		}
		return SettingsMapper.toSettingsDto(settings);
	}

	@Transactional
	public SettingsDTO updateSettings(String key, SettingsDTO settingsDto) {
		Settings settings = settingsRepository.findByKey(key);
		if (settings != null) {
			settings.setValue(settingsDto.getValue());
			settings.setUpdateDate(LocalDateTime.now());
			Settings updatedSettings = settingsRepository.save(settings);
			return SettingsMapper.toSettingsDto(updatedSettings);
		} else {
			throw new EntityNotFoundException("Setting with key " + key + " not found.");
		}
	}
}
