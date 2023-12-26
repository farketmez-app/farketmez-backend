package com.mmhb.farketmez.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.mmhb.farketmez.dto.EventDTO;
import com.mmhb.farketmez.dto.UserDTO;
import com.mmhb.farketmez.dto.UserLoginDTO;
import com.mmhb.farketmez.mapper.EventMapper;
import com.mmhb.farketmez.mapper.UserMapper;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.service.AuthenticationService;
import com.mmhb.farketmez.service.EventService;
import com.mmhb.farketmez.service.ParticipantService;
import com.mmhb.farketmez.service.UserService;
import com.mmhb.farketmez.util.HarvesineDistanceUtil;
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
	private final EventService eventService;

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
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			boolean isPasswordMatch = passwordEncoder.matches(userLoginDto.getPassword(),
					authenticatedUser.getPassword());

			if (isPasswordMatch) {
				String token = jwtUtil.generateToken(authenticatedUser);
				return ResponseEntity.ok(token);
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@GetMapping("/{userId}/all")
	public ResponseEntity<List<EventDTO>> getAllEventsById(@PathVariable Long userId) {
		List<Event> events = eventService.getEventsByCreatorId(userId);
		if (!events.isEmpty()) {
			List<EventDTO> eventDTOS = eventService.getAllEvents().stream().map(EventMapper::toEventDto).toList();
			return new ResponseEntity<>(eventDTOS, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{userId}/events")
	public ResponseEntity<List<EventDTO>> getUserEvents(@PathVariable Long userId) {
		List<Event> events = participantService.getEventsByUser(userId);
		List<EventDTO> eventDTOs = events.stream().map(EventMapper::toEventDto).collect(Collectors.toList());
		return ResponseEntity.ok(eventDTOs);
	}
}
