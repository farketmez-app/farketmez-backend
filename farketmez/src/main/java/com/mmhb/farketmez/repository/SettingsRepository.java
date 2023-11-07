package com.mmhb.farketmez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmhb.farketmez.model.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
	Settings findByKey(String key);
}
