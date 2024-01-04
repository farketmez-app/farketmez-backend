package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.UserTypeDTO;
import com.mmhb.farketmez.model.UserType;

public class UserTypeMapper {
	private UserTypeMapper() {

	}

	public static UserTypeDTO toUserTypeDto(UserType userType) {
		if (userType == null) {
			return null;
		}
		return new UserTypeDTO(userType.getId(), userType.getType());
	}

	public static UserType fromUserTypeDto(UserTypeDTO userTypeDto) {
		if (userTypeDto == null) {
			return null;
		}
		UserType userType = new UserType();
		userType.setId(userTypeDto.getId());
		userType.setType(userTypeDto.getType());
		return userType;
	}
}
