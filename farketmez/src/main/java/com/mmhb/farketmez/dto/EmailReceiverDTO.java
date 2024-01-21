package com.mmhb.farketmez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailReceiverDTO {
	private String mail;

	public String getMail() {
		return mail;
	}
}