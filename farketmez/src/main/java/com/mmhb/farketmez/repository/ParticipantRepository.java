package com.mmhb.farketmez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	@Query("SELECT e FROM Participant p JOIN p.event e WHERE p.userId = ?1")
	List<Event> findEventsByUserId(Long userId);
}