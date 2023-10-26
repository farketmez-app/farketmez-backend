package com.mmhb.farketmez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}