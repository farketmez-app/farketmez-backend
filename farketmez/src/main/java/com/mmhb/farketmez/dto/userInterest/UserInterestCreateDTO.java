package com.mmhb.farketmez.dto.userInterest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInterestCreateDTO {
    Long userId;
    Long interestId;
}
