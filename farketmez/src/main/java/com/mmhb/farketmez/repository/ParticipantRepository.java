package com.mmhb.farketmez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}