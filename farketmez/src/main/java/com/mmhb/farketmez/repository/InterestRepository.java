package com.mmhb.farketmez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.type.InterestType;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
	Interest findByInterestName(InterestType interestName);
}