package com.mmhb.farketmez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.model.UserInterest;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
	List<UserInterest> findByUserId(Long userId);

	@Query("SELECT i FROM Interest i JOIN UserInterest ui ON i = ui.interest WHERE ui.user.id = :userId")
	List<Interest> findInterestsByUserId(Long userId);
}