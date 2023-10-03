package com.mmhb.farketmez.model;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	private Long id;
	private String username;
	private String password;
	private String nameSurname;
	private Integer age;
	private Integer gender;
	private String longitude;
	private String latitude;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "deleted_at")
	private Date deletedAt;

	@ManyToOne
	@JoinColumn(name = "user_type_id")
	private UserType userType;
}