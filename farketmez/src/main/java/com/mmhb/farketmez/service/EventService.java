package com.mmhb.farketmez.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.mmhb.farketmez.exception.OperationNotAllowedException;
import com.mmhb.farketmez.exception.UserInputException;
import com.mmhb.farketmez.model.Event;
import com.mmhb.farketmez.model.Location;
import com.mmhb.farketmez.model.Participant;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.repository.EventRepository;
import com.mmhb.farketmez.repository.LocationRepository;
import com.mmhb.farketmez.repository.ParticipantRepository;
import com.mmhb.farketmez.repository.UserInterestRepository;
import com.mmhb.farketmez.repository.UserRepository;
import com.mmhb.farketmez.util.HarvesineDistanceUtil;
import com.mmhb.farketmez.util.RandomStringUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final UserRepository userRepository;
	private final ParticipantRepository participantRepository;
	private final UserInterestRepository userInterestRepository;
	private final LocationRepository locationRepository;

	@Transactional
	public Event createEvent(Event event) {
		boolean isTitleEmpty = event.getTitle() == null || event.getTitle().isEmpty();
		boolean isDescriptionEmpty = event.getDescription() == null || event.getDescription().isEmpty();
		boolean isDateNull = event.getDate() == null;
		boolean isLocationIdNull = event.getLocation().getId() == null;

		if (isTitleEmpty && isDescriptionEmpty && isDateNull) {
			throw new UserInputException(
					"All fields are empty. Cannot create an event without title, description, and date.");
		}

		if (isLocationIdNull) {
			Double latitude = event.getLocation().getLatitude();
			Double longitude = event.getLocation().getLongitude();
			String googleMapsUrl = event.getLocation().getGoogleMapsUrl();

			if (latitude == null || longitude == null) {
				throw new UserInputException(
						"Location ID is not provided. Latitude and longitude are required to create a location.");
			}

			Location location = new Location();
			location.setLatitude(latitude);
			location.setLongitude(longitude);

			location.setGoogleMapsUrl(googleMapsUrl);

			location = locationRepository.save(location);
			event.setLocation(location);
		}

		if (event.getIsPrivate()) {
			event.setAccessKey(RandomStringUtil.generateRandomString(10));
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

		if (priority.equals("rating")) {
			events = events.stream().filter(c -> c.getCost().equals(cost)).filter(c -> c.getPlace().equals(place))
					.filter(c -> c.getAverageRating().doubleValue() > 2.5)
					.sorted(Comparator.comparing(Event::getAverageRating).reversed()).toList();
		}

		if (priority.equals("attedance")) {
			events = events.stream().filter(c -> c.getCost().equals(cost)).filter(c -> c.getPlace().equals(place))
					.sorted(Comparator.comparingInt((Event e) -> getAttendanceCount(e.getId())).reversed()).toList();
		}

		if (priority.equals("all")) {
			events = events.stream().filter(c -> c.getCost().equals(cost)).filter(c -> c.getPlace().equals(place))
					.filter(c -> c.getAverageRating().doubleValue() > 2.5)
					.sorted(Comparator.comparing(Event::getAverageRating)
							.thenComparingInt((Event e) -> getAttendanceCount(e.getId())).reversed())
					.toList();
		}

		if (events.isEmpty() || events == null) {
			return null;
		}

		return events;
	}

	public List<Event> getNearEvents(Double latitude, Double longitude) {
		List<Event> events = eventRepository.findEventsByIsActiveTrueAndIsPrivateFalse();
		HarvesineDistanceUtil.BoundingBox boundingBox = HarvesineDistanceUtil.calculateBoundingBox(latitude, longitude,
				1.0);

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

			return new ArrayList<>();
		}

		return new ArrayList<>();
	}

	@Transactional
	public Event updateEvent(Event event) {
		if (event.getId() == null || !eventRepository.existsById(event.getId())) {
			throw new UserInputException("Event not found with id: " + event.getId());
		}

		boolean isTitleEmpty = event.getTitle() == null || event.getTitle().isEmpty();
		boolean isDescriptionEmpty = event.getDescription() == null || event.getDescription().isEmpty();
		boolean isDateNull = event.getDate() == null;

		if (isTitleEmpty && isDescriptionEmpty && isDateNull) {
			throw new UserInputException(
					"All fields are empty. Cannot update event without title, description, and date.");
		}

		event.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return eventRepository.save(event);
	}

	@Transactional
	public void deleteEvent(Long id) {
		eventRepository.deleteById(id);
	}

	private int getAttendanceCount(long eventId) {
		return participantRepository.countByEventId(eventId);
	}

	public Event getSuggestedEvent(Long userId) {
		List<Participant> userParticipant = participantRepository.findByUserId(userId);
		List<Event> usersOldEvents = participantRepository.findEventsByUserId(userId);
		Map<Long, Double> oldEventRatings = new HashMap<>();

		Map<String, Integer> placeAndCostCounts = calculatePlaceAndCostCounts(usersOldEvents);

		for (Participant participant : userParticipant) {
			Long eventId = participant.getEvent().getId();
			BigDecimal rating = participant.getRating();
			if (rating == null) {
				rating = BigDecimal.valueOf(0);
			}
			oldEventRatings.put(eventId, rating.doubleValue());
		}

		if (usersOldEvents.isEmpty()) {
			throw new OperationNotAllowedException("User has no past events");
		}

		Event suggestedEvent = recommendEvent(usersOldEvents, oldEventRatings, placeAndCostCounts);

		if (suggestedEvent == null) {
			throw new UserInputException("No events found with those variables.");
		}

		return suggestedEvent;
	}

	private Event recommendEvent(List<Event> usersOldEvents, Map<Long, Double> oldEventRatings,
			Map<String, Integer> placeAndCostCounts) {
		Event suggestedEvent = null;
		List<Event> activeEvents = eventRepository.findEventsByIsActiveTrueAndIsPrivateFalse();
		double maxScore = Double.MIN_VALUE;

		for (Event activeEvent : activeEvents) {
			if (!usersOldEvents.contains(activeEvent)) {
				double score = calculateScore(activeEvent, oldEventRatings, placeAndCostCounts);
				if (score > maxScore) {
					maxScore = score;
					suggestedEvent = activeEvent;
				}
			}
		}

		return suggestedEvent;
	}

	private double calculateScore(Event event, Map<Long, Double> oldEventRatings,
			Map<String, Integer> placeAndCostCounts) {
		double ratingWeight = 0.5;

		Pair<Double, Double> placeScoreAndCostScore = calculatePlaceScore(event, placeAndCostCounts,
				oldEventRatings.size());
		double ratingScore = oldEventRatings.getOrDefault(event.getId(), 0.0);

		return (ratingScore * ratingWeight) + placeScoreAndCostScore.getFirst() + placeScoreAndCostScore.getSecond();
	}

	private Pair<Double, Double> calculatePlaceScore(Event activeEvent, Map<String, Integer> placeAndCostCounts,
			int oldEventSize) {
		if (activeEvent.getPlace().equalsIgnoreCase("outdoor")) {
			placeAndCostCounts.put("outdoor", placeAndCostCounts.getOrDefault("outdoor", 0) + 10);
		} else if (activeEvent.getPlace().equalsIgnoreCase("home")) {
			placeAndCostCounts.put("home", placeAndCostCounts.getOrDefault("home", 0) + 10);
		} else if (activeEvent.getPlace().equalsIgnoreCase("place")) {
			placeAndCostCounts.put("place", placeAndCostCounts.getOrDefault("place", 0) + 10);
		}

		if (activeEvent.getCost().equalsIgnoreCase("cheap")) {
			placeAndCostCounts.put("cheap", placeAndCostCounts.getOrDefault("cheap", 0) + 10);
		} else if (activeEvent.getCost().equalsIgnoreCase("mid")) {
			placeAndCostCounts.put("mid", placeAndCostCounts.getOrDefault("mid", 0) + 10);
		} else if (activeEvent.getCost().equalsIgnoreCase("expensive")) {
			placeAndCostCounts.put("expensive", placeAndCostCounts.getOrDefault("expensive", 0) + 10);
		}

		double outdoorScore = calculateWeightedScore(placeAndCostCounts.getOrDefault("outdoor", 0), oldEventSize, 0.3);
		double homeScore = calculateWeightedScore(placeAndCostCounts.getOrDefault("home", 0), oldEventSize, 0.3);
		double placeScore = calculateWeightedScore(placeAndCostCounts.getOrDefault("place", 0), oldEventSize, 0.4);
		double totalPlaceScore = Math.max(outdoorScore, Math.max(homeScore, placeScore))
				/ (outdoorScore + homeScore + placeScore);
		/*
		 * System.out.println(
		 * "--------------------------------------------------------------");
		 * System.out.println(activeEvent.getId() + " : outdoor score " + outdoorScore);
		 * System.out.println(activeEvent.getId() + " : home score " + homeScore);
		 * System.out.println(activeEvent.getId() + " : place score " + placeScore);
		 * System.out.println(activeEvent.getId() + " : Total Place Score is -> " +
		 * totalPlaceScore);
		 */

		double cheapScore = calculateWeightedScore(placeAndCostCounts.getOrDefault("cheap", 0), oldEventSize, 0.4);
		double midScore = calculateWeightedScore(placeAndCostCounts.getOrDefault("mid", 0), oldEventSize, 0.4);
		double expensiveScore = calculateWeightedScore(placeAndCostCounts.getOrDefault("expensive", 0), oldEventSize,
				0.4);
		double totalCostScore = Math.max(cheapScore, Math.max(midScore, expensiveScore))
				/ (cheapScore + midScore + expensiveScore);
		/*
		 * System.out.println(
		 * "--------------------------------------------------------------");
		 * System.out.println(activeEvent.getId() + " : Cheap score " + cheapScore);
		 * System.out.println(activeEvent.getId() + " : mid score " + midScore);
		 * System.out.println(activeEvent.getId() + " : expensive score " +
		 * expensiveScore); System.out.println(activeEvent.getId() +
		 * " : Total Cost Score is -> " + totalCostScore); System.out.println(
		 * "--------------------------------------------------------------\n\n\n");
		 */

		return Pair.of(totalPlaceScore, totalCostScore);
	}

	private Map<String, Integer> calculatePlaceAndCostCounts(List<Event> usersOldEvents) {
		Map<String, Integer> countsMap = new HashMap<>();
		int outdoorCount = 0, homeCount = 0, placeCount = 0, cheapCount = 0, midCount = 0, expensiveCount = 0;

		for (Event e : usersOldEvents) {
			if (e.getPlace().equalsIgnoreCase("outdoor")) {
				outdoorCount += 1;
			} else if (e.getPlace().equalsIgnoreCase("home")) {
				homeCount += 1;
			} else if (e.getPlace().equalsIgnoreCase("place")) {
				placeCount += 1;
			} else if (e.getPlace().equalsIgnoreCase("farketmez")) {
				outdoorCount += 1;
				homeCount += 1;
				placeCount += 1;
			}

			if (e.getCost().equalsIgnoreCase("cheap")) {
				cheapCount += 1;
			} else if (e.getCost().equalsIgnoreCase("mid")) {
				midCount += 1;
			} else if (e.getCost().equalsIgnoreCase("expensive")) {
				expensiveCount += 1;
			} else if (e.getCost().equalsIgnoreCase("farketmez")) {
				cheapCount += 1;
				midCount += 1;
				expensiveCount += 1;
			}
		}

		countsMap.put("outdoor", outdoorCount);
		countsMap.put("home", homeCount);
		countsMap.put("place", placeCount);
		countsMap.put("cheap", cheapCount);
		countsMap.put("mid", midCount);
		countsMap.put("expensive", expensiveCount);

		return countsMap;
	}

	private double calculateWeightedScore(int count, int totalEvents, double weight) {
		return (double) count / totalEvents * weight;
	}

	public List<Event> getPastEvents() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return eventRepository.findAll().stream().filter(e -> e.getDate().before(now)).collect(Collectors.toList());
	}

	public List<Event> getUpcomingEvents() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return eventRepository.findAll().stream().filter(e -> e.getDate().after(now)).collect(Collectors.toList());
	}

	public void joinEvent(Long userId, Long eventId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new OperationNotAllowedException("User not found"));
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new OperationNotAllowedException("Event not found"));

		Participant participant = new Participant();
		participant.setUser(user);
		participant.setEvent(event);

		participantRepository.save(participant);
	}

	public Event joinPrivateEvent(String accessKey, Long userId) {
		Event event = eventRepository.findEventByAccessKey(accessKey).orElse(null);
		if (event == null) {
			throw new UserInputException("Event not found with this access key");
		}

		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new UserInputException("User not found with this id");
		}

		Participant participant = new Participant();
		participant.setEvent(event);
		participant.setUser(user);
		participantRepository.save(participant);

		return event;
	}

	public Event findEventWithParams(String place, String cost, String date, String time, List<String> pool,
			Long userId) {
		if (place == null || cost == null || date == null || pool == null || userId == null) {
			throw new UserInputException("Fields are not filled correctly.");
		}

		List<Event> events;

		if (pool.size() < 2) {
			if (pool.contains("public-events")) {
				events = eventRepository.findEventsByIsPrivateFalse();
			} else if (pool.contains("my-events")) {
				events = eventRepository.findEventsByCreatorId(userId);
			} else {
				events = Collections.emptyList();
			}
		} else {
			events = eventRepository.findEventsByIsPrivateFalse().stream().filter(c -> !c.getCreatorId().equals(userId))
					.collect(Collectors.toList());
			List<Event> userEvents = eventRepository.findEventsByCreatorId(userId);
			events.addAll(userEvents);
		}

		if (events.isEmpty()) {
			throw new EntityNotFoundException("No events were found");
		}

		if (!place.equalsIgnoreCase("all")) {
			events = events.stream().filter(c -> c.getPlace().equalsIgnoreCase(place)).collect(Collectors.toList());
		}

		if (!cost.equalsIgnoreCase("all")) {
			events = events.stream().filter(c -> c.getCost().equalsIgnoreCase(cost)).collect(Collectors.toList());
		}

		if (time == null || time.trim().isEmpty()) {
			time = "00-23";
		}

		String[] timeRange = time.split("-");
		if (timeRange.length != 2) {
			throw new IllegalArgumentException("Invalid time format.");
		}

		LocalTime startTime = LocalTime.parse(timeRange[0].trim() + ":00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));
		LocalTime endTime = LocalTime.parse(timeRange[1].trim() + ":59:00", DateTimeFormatter.ofPattern("HH:mm:ss"));
		LocalDate localDate = LocalDate.parse(date.trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

		LocalDateTime startDate = LocalDateTime.of(localDate, startTime);
		LocalDateTime endDate = LocalDateTime.of(localDate, endTime);

		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String startDateStr = startDate.format(customFormatter);
		String endDateStr = endDate.format(customFormatter);

		events = events.stream().filter(c -> c.getDate().after(Timestamp.valueOf(startDateStr))
				&& c.getDate().before(Timestamp.valueOf(endDateStr))).collect(Collectors.toList());

		if (events.isEmpty()) {
			throw new EntityNotFoundException("No events were found");
		}

		int randomIndex = ThreadLocalRandom.current().nextInt(events.size());

		return events.get(randomIndex);
	}

	public List<Event> searchEvents(String searchQuery) {
		if (searchQuery == null || searchQuery.trim().isEmpty()) {
			return getAllEvents();
		}

		return eventRepository.findAll().stream()
				.filter(event -> event.getTitle().toLowerCase().contains(searchQuery.toLowerCase())
						|| event.getDescription().toLowerCase().contains(searchQuery.toLowerCase())
						|| event.getCost().toLowerCase().contains(searchQuery.toLowerCase())
						|| event.getPlace().toLowerCase().contains(searchQuery.toLowerCase()))
				.collect(Collectors.toList());
	}
//Example request:http://localhost:8080/events/filter?cost=ucuz&place=mekanda&priority=rating

	public List<Event> getFilteredEvents(String cost, String place, String priority) {
		Stream<Event> eventsStream = eventRepository.findEventsByIsActiveTrueAndIsPrivateFalse().stream();

		if (cost != null && !cost.isEmpty() && !cost.equalsIgnoreCase("all")) {
			eventsStream = eventsStream.filter(event -> event.getCost().equalsIgnoreCase(cost));
		}
		if (place != null && !place.isEmpty() && !place.equalsIgnoreCase("all")) {
			eventsStream = eventsStream.filter(event -> event.getPlace().equalsIgnoreCase(place));
		}

		if ("rating".equals(priority)) {
			eventsStream = eventsStream
					.filter(event -> event.getAverageRating() != null && event.getAverageRating().doubleValue() > 2.5)
					.sorted(Comparator.comparing(Event::getAverageRating).reversed());
		} else if ("attendance".equals(priority)) {
			eventsStream = eventsStream
					.sorted(Comparator.comparingInt((Event e) -> getAttendanceCount(e.getId())).reversed());
		} else if ("all".equals(priority)) {
			eventsStream = eventsStream
					.filter(event -> event.getAverageRating() != null && event.getAverageRating().doubleValue() > 2.5)
					.sorted(Comparator.comparing(Event::getAverageRating)
							.thenComparingInt((Event e) -> getAttendanceCount(e.getId())).reversed());
		}

		return eventsStream.collect(Collectors.toList());
	}
}
