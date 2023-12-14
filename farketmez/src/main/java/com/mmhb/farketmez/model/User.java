package com.mmhb.farketmez.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "username", nullable = false, length = 50)
	private String username;

	@Column(name = "password", nullable = false, length = 50)
	private String password;

	@Column(name = "name", length = 30)
	private String name;

	@Column(name = "surname", length = 30)
	private String surname;

	@Column(name = "age")
	private Integer age;

	@Column(name = "gender")
	private Integer gender;

	@Column(name = "longitude", length = 100)
	private String longitude;

	@Column(name = "latitude", length = 100)
	private String latitude;

	@Column(name = "mail", nullable = false, length = 30) // FIXME:İleride bu değer unique olarak düzeltilmelidir.
	private String mail;
	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "deleted_at")
	private Timestamp deletedAt;

	@ManyToOne
	@JoinColumn(name = "user_type_id")
	private UserType userType;

	public User(String username, String password, String name, String surname, Integer age, Integer gender,
			String longitude, String latitude, String mail, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt, UserType userType) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.gender = gender;
		this.longitude = longitude;
		this.latitude = latitude;
		this.mail = mail;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.userType = userType;
	}

	public User(Long id, String username, String password, String name, String surname, Integer age, Integer gender,
			String longitude, String latitude, String mail, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt, UserType userType) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.gender = gender;
		this.longitude = longitude;
		this.latitude = latitude;
		this.mail = mail;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.userType = userType;
	}
}
