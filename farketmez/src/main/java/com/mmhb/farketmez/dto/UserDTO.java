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
	private String surname;
	private Integer age;
	private Integer gender;
	private String longitude;
	private String latitude;
	private String mail;
	private UserType userType;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;
}
