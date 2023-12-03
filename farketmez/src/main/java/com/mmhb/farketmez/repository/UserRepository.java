package com.mmhb.farketmez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByMail(String mail);
}