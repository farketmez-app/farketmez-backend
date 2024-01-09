package com.mmhb.farketmez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.UserType;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}