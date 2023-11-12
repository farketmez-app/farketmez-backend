package com.mmhb.farketmez.mapper;

import com.mmhb.farketmez.dto.InterestDTO;
import com.mmhb.farketmez.model.Interest;

public class InterestMapper {
    private InterestMapper() {

    }

    public static InterestDTO toInterestDto(Interest interest) {
        return new InterestDTO(interest.getId(), interest.getInterestName());
    }

    public static Interest fromInterestDto(InterestDTO interestDTO) {
        return new Interest(interestDTO.getId(), interestDTO.getInterestName());
    }
}
