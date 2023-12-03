package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.UserInterestDTO;
import com.mmhb.farketmez.model.UserInterest;

public class UserInterestMapper {
    private UserInterestMapper(){

    }

    public static UserInterestDTO toUserInterestDto(UserInterest userInterest){
        return new UserInterestDTO(userInterest.getId(), userInterest.getUserId(), userInterest.getInterestId());
    }

    public static UserInterest fromUserInterest(UserInterestDTO userInterestDto){
        return new UserInterest(userInterestDto.getUserId(),userInterestDto.getUserId(), userInterestDto.getInterestId());
    }
}
