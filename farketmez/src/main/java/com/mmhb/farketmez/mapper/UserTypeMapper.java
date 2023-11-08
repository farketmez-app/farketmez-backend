package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.UserTypeDTO;
import com.mmhb.farketmez.model.UserType;

public class UserTypeMapper {
    private UserTypeMapper() {

    }

    public static UserTypeDTO toUserTypeDto(UserType userType) {
        return new UserTypeDTO(userType.getId(), userType.getType());
    }

    public static UserType fromUserTypeDto(UserTypeDTO userTypeDto) {
        return new UserType(userTypeDto.getId(), userTypeDto.getType());
    }
}
