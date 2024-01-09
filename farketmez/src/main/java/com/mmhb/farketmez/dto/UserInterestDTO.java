package com.mmhb.farketmez.dto;

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInterestDTO {
	private Long id;

	private User user;
	private Interest interest;
}
