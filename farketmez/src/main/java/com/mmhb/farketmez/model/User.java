package com.mmhb.farketmez.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "name", length = 30)
	private String name;

	@Column(name = "lastname", length = 30)
	private String lastname;

	@Column(name = "age")
	private Integer age;

	@Column(name = "gender", length = 30)
	private String gender;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "mail", nullable = false, unique = true)
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

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "user_interests", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "interest_id"))
	private Set<Interest> interests = new HashSet<>();

	public User(String username, String password, String name, String lastname, Integer age, String gender,
			Double longitude, Double latitude, String mail, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt, UserType userType) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
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

	public User(Long id, String username, String password, String name, String lastname, Integer age, String gender,
			Double longitude, Double latitude, String mail, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt, UserType userType) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
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
