package com.mmhb.farketmez.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User authenticateUser(String email, String password) {
		User user = userRepository.findByMail(email);
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}
		return null;
	}

	public User getAuthenticatedUser() {
		// FIXME: Oturum açan kullanıcı bilgisi bir şekilde alınmalı.
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
