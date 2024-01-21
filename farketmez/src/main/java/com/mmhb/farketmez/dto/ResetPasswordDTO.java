package com.mmhb.farketmez.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {
	private String hash;
	private String password;
	private String confirmPassword;
}
