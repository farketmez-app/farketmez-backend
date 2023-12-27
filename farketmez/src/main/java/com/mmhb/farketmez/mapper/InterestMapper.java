package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.InterestDTO;
import com.mmhb.farketmez.model.Interest;

public class InterestMapper {
	private InterestMapper() {

	}

	public static InterestDTO toInterestDto(Interest interest) {
		InterestDTO dto = new InterestDTO();
		dto.setId(interest.getId());
		dto.setInterestName(interest.getInterestName());
		return dto;
	}

	public static Interest fromInterestDto(InterestDTO interestDTO) {
		Interest interest = new Interest();
		interest.setId(interestDTO.getId());
		interest.setInterestName(interestDTO.getInterestName());
		return interest;
	}
}
