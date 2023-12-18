package com.mmhb.farketmez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByCreatorId(Long creatorId);
    List<Event> findEventsByIsActiveFalse();
}