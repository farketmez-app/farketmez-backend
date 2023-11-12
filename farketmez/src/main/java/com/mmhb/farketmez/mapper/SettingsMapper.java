package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.SettingsDTO;
import com.mmhb.farketmez.model.Settings;

public class SettingsMapper {
	private SettingsMapper() {
	}

	public static SettingsDTO toSettingsDto(Settings settings) {
		return new SettingsDTO(settings.getId(), settings.getKey(), settings.getValue());
	}

	public static Settings fromSettingsDTO(SettingsDTO settingDto) {
		Settings settings = new Settings();
		settings.setId(settingDto.getId());
		settings.setKey(settingDto.getKey());
		settings.setValue(settingDto.getValue());
		return settings;
	}
}
