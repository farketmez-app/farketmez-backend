package com.mmhb.farketmez.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	@Column(name = "name_surname")
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

	public User(String username, String password, String nameSurname, int age, int gender, String longitude, String latitude) {
		this.username = username;
		this.password = password;
		this.nameSurname = nameSurname;
		this.age = age;
		this.gender = gender;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}