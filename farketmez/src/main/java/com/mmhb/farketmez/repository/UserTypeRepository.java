package com.mmhb.farketmez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.type.UserTypeEnum;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
	UserType findByType(UserTypeEnum type);
}