package com.mmhb.farketmez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginDTO {
	private Long id;
	private String mail;
	private String password;
}