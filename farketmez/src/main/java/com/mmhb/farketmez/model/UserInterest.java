package com.mmhb.farketmez.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "user_interests")
@Entity
public class UserInterest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private final Long id;

	@Column(name = "user_id")
	private final Long userId;

	@Column(name = "interest_id")
	private final Long interestId;

	public UserInterest(Long id, Long userId, Long interestId) {
		this.id = id;
		this.userId = userId;
		this.interestId = interestId;
	}
}
