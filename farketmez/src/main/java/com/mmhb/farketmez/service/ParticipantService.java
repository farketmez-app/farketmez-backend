package com.mmhb.farketmez.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.RateRequestDTO;
import com.mmhb.farketmez.exception.CustomOperationException;
import com.mmhb.farketmez.exception.DatabaseOperationException;
import com.mmhb.farketmez.exception.OperationNotAllowedException;
import com.mmhb.farketmez.exception.UserInputException;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.repository.EventRepository;
import com.mmhb.farketmez.repository.ParticipantRepository;
import com.mmhb.farketmez.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantService {

	private final ParticipantRepository participantRepository;
	private final UserRepository userRepository;
	private final EventRepository eventRepository;

	@Transactional
	public Participant createParticipant(Participant participant) {
		if (participant.getUser() == null && participant.getRating() == null && participant.getEvent() == null) {
			throw new OperationNotAllowedException(
					"Missing required fields. User, Rating, and Event must be provided.");
		}
		// FIXME: Puanlama için rating değeri 0 ile 5 aralığında olarak kabul edilmiştir
		// ileride ihtiyaca göre değiştirilmelidir.
		if (participant.getRating().compareTo(BigDecimal.ZERO) < 0
				|| participant.getRating().compareTo(new BigDecimal("5")) > 0) {
			throw new UserInputException("Rating must be between 0 and 5.");
		}
		return participantRepository.save(participant);
	}

	public List<Participant> getAllParticipants() {
		return participantRepository.findAll();
	}

	public Participant getParticipantById(Long id) {
		return participantRepository.findById(id).orElse(null);
	}

	@Transactional
	public Participant updateParticipant(Participant participant) {
		if (participant.getId() == null || !participantRepository.existsById(participant.getId())) {
			throw new UserInputException("Participant not found with id: " + participant.getId());
		}

		if (participant.getUser() == null && participant.getRating() == null && participant.getEvent() == null) {
			throw new UserInputException("Missing required fields. User ID, Rating, and Event must be provided.");
		}
		// FIXME: Puanlama için rating değeri 0 ile 5 aralığında olarka kabul edilmiştir
		// ileride ihtiyaca göre değiştirilmelidir.
		if (participant.getRating().compareTo(BigDecimal.ZERO) < 0
				|| participant.getRating().compareTo(new BigDecimal("5")) > 0) {
			throw new UserInputException("Rating must be between 0 and 5.");
		}
		return participantRepository.save(participant);
	}

	@Transactional
	public void deleteParticipant(Long id) {
		participantRepository.deleteById(id);
	}

	public List<Event> getEventsByUser(Long userId) {
		return participantRepository.findEventsByUserId(userId);
	}

	public List<Participant> getParticipantsByUserId(Long userId) {
		return participantRepository.findByUserId(userId);
	}

	@Transactional
	public void rateEvent(RateRequestDTO rateRequestDTO) {
		User user = userRepository.findById(rateRequestDTO.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		Event event = eventRepository.findById(rateRequestDTO.getEventId())
				.orElseThrow(() -> new DatabaseOperationException("Event not found"));
		if (event.getDate().after(new Timestamp(System.currentTimeMillis()))) {
			throw new CustomOperationException("Cannot rate an ongoing event.");
		}
		Participant participant = new Participant();
		participant.setUser(user);
		participant.setEvent(event);
		participant.setRating(rateRequestDTO.getRate());
		participant.setComment(rateRequestDTO.getComment());

		participantRepository.save(participant);
	}

	@Transactional
	public void editEventRate(Long userId, Long eventId, BigDecimal rating, String comment) {
		Participant participant = participantRepository.findByUserIdAndEventId(userId, eventId)
				.orElseThrow(() -> new OperationNotAllowedException("Rating not found"));

		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new OperationNotAllowedException("Event not found"));

		// Etkinliği oylayabilmek için bitmesi şartı aranmaktadır.
		if (event.getDate().after(new Timestamp(System.currentTimeMillis()))) {
			throw new OperationNotAllowedException("Cannot edit rating for an ongoing event.");
		}

		participant.setRating(rating);
		participant.setComment(comment);

		participantRepository.save(participant);
	}

	public List<Event> getAttendedRatedExpiredEvents(Long userId) {
		return participantRepository.findAttendedRatedExpiredEvents(userId);
	}

	public List<Event> getAttendedNotRatedExpiredEvents(Long userId) {
		return participantRepository.findAttendedNotRatedExpiredEvents(userId);
	}

	public List<Event> getAttendedNotRatedNotExpiredEvents(Long userId) {
		return participantRepository.findAttendedNotRatedNotExpiredEvents(userId);
	}

}
