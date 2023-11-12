package com.mmhb.farketmez.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "interests")
@Data
@Entity
@NoArgsConstructor
public class Interest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "interest_name")
	private String interestName;

	public Interest(Long id, String interestName) {
		this.id = id;
		this.interestName = interestName;
	}
}
