package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.dto.UserDTO;
import com.mmhb.farketmez.dto.UserLoginDTO;
import com.mmhb.farketmez.mapper.EventMapper;
import com.mmhb.farketmez.mapper.UserMapper;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.service.AuthenticationService;
import com.mmhb.farketmez.service.ParticipantService;
import com.mmhb.farketmez.service.UserService;
import com.mmhb.farketmez.util.JwtUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/users")
public class UserController {
	private final UserService userService;
	private final AuthenticationService authenticationService;
	private final JwtUtil jwtUtil;
	private final ParticipantService participantService;

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> users = userService.getAllUsers().stream().map(UserMapper::toUserDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		if (user != null) {
			UserDTO userDTO = UserMapper.toUserDto(user);
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		User user = UserMapper.fromUserDto(userDTO);
		User createdUser = userService.createUser(user);
		return new ResponseEntity<>(UserMapper.toUserDto(createdUser), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
		User user = UserMapper.fromUserDto(userDTO);
		User updatedUser = userService.updateUser(user);
		if (updatedUser != null) {
			return new ResponseEntity<>(UserMapper.toUserDto(updatedUser), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		try {
			userService.deleteUser(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDto) {
		User authenticatedUser = authenticationService.authenticateUser(userLoginDto.getMail(),
				userLoginDto.getPassword());
		if (authenticatedUser != null) {
			String token = jwtUtil.generateToken(authenticatedUser);
			// TODO: Consider returning those with another DTO
			return ResponseEntity.ok().body("token: " + token + "\n" +
					"id: " + authenticatedUser.getId());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@GetMapping("/{userId}/events")
	public ResponseEntity<List<EventDTO>> getUserEvents(@PathVariable Long userId) {
		List<Event> events = participantService.getEventsByUser(userId);
		List<EventDTO> eventDTOs = events.stream().map(EventMapper::toEventDto).collect(Collectors.toList());
		return ResponseEntity.ok(eventDTOs);
	}
}
