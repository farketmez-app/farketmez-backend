package com.mmhb.farketmez.repository;

import com.mmhb.farketmez.type.InterestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.Interest;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    Interest findByInterestName(String name);
}