package com.mmhb.farketmez.model;

import java.util.HashSet;
import java.util.Set;

import com.mmhb.farketmez.type.InterestType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "interest_name")
	private InterestType interestName;

	@ManyToMany(mappedBy = "interests", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<User> users = new HashSet<>();

	public Interest(Long id, InterestType interestName) {
		this.id = id;
		this.interestName = interestName;
	}
}
