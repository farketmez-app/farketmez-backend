package com.mmhb.farketmez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SettingsDTO {
	Long id;
	String key;
	String value;
}
