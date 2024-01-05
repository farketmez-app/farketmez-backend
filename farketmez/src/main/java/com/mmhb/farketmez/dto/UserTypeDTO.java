package com.mmhb.farketmez.dto;

import com.mmhb.farketmez.type.UserTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTypeDTO {
	private Long id;
	private UserTypeEnum type;
}
