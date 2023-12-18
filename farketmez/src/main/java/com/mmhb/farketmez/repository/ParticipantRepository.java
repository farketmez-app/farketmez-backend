package com.mmhb.farketmez.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	@Query("SELECT p.event FROM Participant p WHERE p.user.id = ?1")
	List<Event> findEventsByUserId(Long userId);

	Optional<Participant> findByUserIdAndEventId(Long userId, Long eventId);
}