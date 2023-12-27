package com.mmhb.farketmez.dto;

import com.mmhb.farketmez.type.InterestType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InterestDTO {
	private Long id;
	private InterestType interestName;
}
