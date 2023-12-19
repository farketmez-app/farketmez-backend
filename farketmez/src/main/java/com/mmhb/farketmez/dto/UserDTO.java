package com.mmhb.farketmez.dto;

import java.sql.Timestamp;

import com.mmhb.farketmez.model.UserType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
	private Long id;
	private String username;
	private String password;
	private String name;
	private String lastname;
	private Integer age;
	private String gender;
	private Double longitude;
	private Double latitude;
	private String mail;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	private UserType userType;
}
