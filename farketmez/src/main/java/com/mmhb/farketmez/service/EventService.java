package com.mmhb.farketmez.service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.repository.ParticipantRepository;
import com.mmhb.farketmez.repository.UserRepository;
import com.mmhb.farketmez.util.HarvesineDistanceUtil;
import org.springframework.stereotype.Service;

import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.repository.EventRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final UserRepository userRepository;
	private final ParticipantRepository participantRepository;

	@Transactional
	public Event createEvent(Event event) {
		if (event.getTitle() == null || event.getTitle().isEmpty() || event.getDescription() == null
				|| event.getDescription().isEmpty() || event.getDate() == null) {
			throw new IllegalArgumentException(
					"Missing or incorrect event information. Please fill in all required fields.");
		}

		event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		return eventRepository.save(event);
	}

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	public Event getEventById(Long id) {
		return eventRepository.findById(id).orElse(null);
	}

	public List<Event> getEventsByCreatorId(Long id) {
		return eventRepository.findEventsByCreatorId(id);
	}

	public List<Event> getPublicEvents(String cost, String place, String priority) {
		List<Event> events = eventRepository.findEventsByIsActiveTrueAndIsPrivateFalse();

		if(priority.equals("rating")){
			events = events.stream()
					.filter(c -> c.getCost().equals(cost))
					.filter(c -> c.getPlace().equals(place))
					.filter(c -> c.getAverageRating().doubleValue() > 2.5)
					.sorted(Comparator.comparing(Event::getAverageRating).reversed()).toList();
		}

		if(priority.equals("attedance")){
			events = events.stream()
					.filter(c -> c.getCost().equals(cost))
					.filter(c -> c.getPlace().equals(place))
					.sorted(Comparator.comparingInt((Event e) -> getAttendanceCount(e.getId())).reversed())
					.toList();
		}

		if (priority.equals("all")) {
			events = events.stream()
					.filter(c -> c.getCost().equals(cost))
					.filter(c -> c.getPlace().equals(place))
					.filter(c -> c.getAverageRating().doubleValue() > 2.5)
					.sorted(Comparator.comparing(Event::getAverageRating)
							.thenComparingInt((Event e) -> getAttendanceCount(e.getId()))
							.reversed())
					.toList();
		}


		if(events.isEmpty() || events == null) {
			return null;
		}


		return events;
	}

	public List<Event> getNearEvents(Double latitude, Double longitude){
		List<Event> events = eventRepository.findEventsByIsActiveTrueAndIsPrivateFalse();
			HarvesineDistanceUtil.BoundingBox boundingBox = HarvesineDistanceUtil.calculateBoundingBox(latitude,
					longitude, 0.5);

			if (!events.isEmpty()) {
				events = events
						.stream().filter(c -> c.getIsActive()).filter(c -> c.getLocation() != null
								&& c.getLocation().getLatitude() != null && c.getLocation().getLongitude() != null)
						.filter(c -> {
							try {
								double eventLatitude = c.getLocation().getLatitude();
								double eventLongitude = c.getLocation().getLongitude();

								return eventLatitude > boundingBox.getMinLatitude()
										&& eventLatitude < boundingBox.getMaxLatitude()
										&& eventLongitude > boundingBox.getMinLongitude()
										&& eventLongitude < boundingBox.getMaxLongitude();
							} catch (NumberFormatException e) {
								return false;
							}
						}).toList();

				if (!events.isEmpty()) {
					return events;
				}

				return null;
			}

		return null;
	}

	@Transactional
	public Event updateEvent(Event event) {
		if (event.getId() == null || !eventRepository.existsById(event.getId())) {
			throw new IllegalArgumentException("Event not found with id: " + event.getId());
		}

		if (event.getTitle() == null || event.getTitle().isEmpty() || event.getDescription() == null
				|| event.getDescription().isEmpty() || event.getDate() == null) {
			throw new IllegalArgumentException(
					"Missing or incorrect event information. Please fill in all required fields.");
		}

		event.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return eventRepository.save(event);
	}

	@Transactional
	public void deleteEvent(Long id) {
		eventRepository.deleteById(id);
	}

	private int getAttendanceCount(long eventId) {
		List<Participant> participants = participantRepository.findByEventId(eventId);
		return participants.size();
	}
}
