package com.mmhb.farketmez.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.dto.SettingsDTO;
import com.mmhb.farketmez.service.SettingsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

	private final SettingsService settingsService;

	@GetMapping("/{key}")
	public ResponseEntity<SettingsDTO> getSettings(@PathVariable(name = "key") String key) {
		SettingsDTO settings = settingsService.getSettings(key);
		return ResponseEntity.ok(settings);
	}

	@PutMapping("/{key}")
	public ResponseEntity<SettingsDTO> updateSettings(@PathVariable(name = "key") String key,
			@RequestBody SettingsDTO settingDto) {
		SettingsDTO updatedSetting = settingsService.updateSettings(key, settingDto);
		return ResponseEntity.ok(updatedSetting);
	}
}
