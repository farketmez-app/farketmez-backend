package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.UserInterestDTO;
import com.mmhb.farketmez.model.UserInterest;

public class UserInterestMapper {
	private UserInterestMapper() {

	}

	public static UserInterestDTO toUserInterestDto(UserInterest userInterest) {
		return new UserInterestDTO(userInterest.getId(), userInterest.getUserId(), userInterest.getInterestId());
	}

	public static UserInterest fromUserInterestDto(UserInterestDTO userInterestDto) {
		return new UserInterest(userInterestDto.getId(), userInterestDto.getUserId(), userInterestDto.getInterestId());
	}
}
