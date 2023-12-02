package com.mmhb.farketmez.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.model.Settings;
import com.mmhb.farketmez.service.SettingsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingsController {

	private final SettingsService settingsService;

	@GetMapping("/{key}")
	public ResponseEntity<Settings> getSettings(@PathVariable(name = "key") String key) {
		try {
			Settings settings = settingsService.getSettings(key);
			return new ResponseEntity<>(settings, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{key}")
	public ResponseEntity<Settings> updateSettings(@PathVariable(name = "key") String key,
			@RequestBody Settings settings) {
		try {
			Settings updatedSettings = settingsService.updateSettings(key, settings);
			return new ResponseEntity<>(updatedSettings, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
