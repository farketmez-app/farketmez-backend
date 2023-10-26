package com.mmhb.farketmez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.EventType;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {
}