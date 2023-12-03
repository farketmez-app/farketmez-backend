package com.mmhb.farketmez.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByMail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(),
				new ArrayList<>());
	}
}
