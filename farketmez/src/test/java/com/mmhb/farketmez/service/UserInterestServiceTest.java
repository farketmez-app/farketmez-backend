package com.mmhb.farketmez.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.model.Interest;
import com.mmhb.farketmez.model.User;
import com.mmhb.farketmez.model.UserInterest;
import com.mmhb.farketmez.repository.InterestRepository;
import com.mmhb.farketmez.repository.UserInterestRepository;
import com.mmhb.farketmez.repository.UserRepository;
import com.mmhb.farketmez.type.InterestType;

class UserInterestServiceTest {

	@Mock
	private UserInterestRepository userInterestRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private InterestRepository interestRepository;

	private UserInterestService userInterestService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		userInterestService = new UserInterestService(userInterestRepository, userRepository, interestRepository);
	}

	@Test
	void whenCreatingUserInterest_thenShouldReturnSavedUserInterest() {
		User user = new User();
		user.setId(1L);
		Interest interest = new Interest();
		interest.setId(2L);

		UserInterest userInterestToSave = new UserInterest(null, user, interest);
		when(userInterestRepository.save(any(UserInterest.class))).thenReturn(userInterestToSave);

		UserInterest actual = userInterestService.createUserInterest(userInterestToSave);

		assertNotNull(actual);
		assertEquals(userInterestToSave.getUser(), actual.getUser());
		assertEquals(userInterestToSave.getInterest(), actual.getInterest());
	}

	@Test
	void whenRetrievingAllUserInterests_thenShouldReturnListOfUserInterests() {
		User user1 = new User();
		user1.setId(1L);
		User user2 = new User();
		user2.setId(2L);
		Interest interest2 = new Interest();
		interest2.setId(2L);
		Interest interest3 = new Interest();
		interest3.setId(3L);

		List<UserInterest> userInterests = Arrays.asList(new UserInterest(1L, user1, interest2),
				new UserInterest(2L, user2, interest3));
		when(userInterestRepository.findAll()).thenReturn(userInterests);

		List<UserInterest> actual = userInterestService.findAll();

		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenUserInterestId_whenRetrievingUserInterest_thenShouldReturnUserInterest() {
		Long userInterestId = 1L;
		User user = new User();
		user.setId(1L);
		Interest interest = new Interest();
		interest.setId(2L);

		UserInterest userInterest = new UserInterest(userInterestId, user, interest);
		when(userInterestRepository.findById(userInterestId)).thenReturn(Optional.of(userInterest));

		UserInterest actual = userInterestService.findById(userInterestId);

		assertNotNull(actual);
		assertEquals(userInterestId, actual.getId());
	}

	@Test
	void givenUserInterestDetails_whenUpdatingUserInterest_thenShouldReturnUpdatedUserInterest() {
		Long userInterestId = 1L;
		User user = new User();
		user.setId(1L);
		Interest interest2 = new Interest();
		interest2.setId(2L);
		Interest interest3 = new Interest();
		interest3.setId(3L);

		UserInterest userInterestToUpdate = new UserInterest(userInterestId, user, interest3);

		when(userInterestRepository.findById(userInterestId))
				.thenReturn(Optional.of(new UserInterest(userInterestId, user, interest2)));

		when(userInterestRepository.save(any(UserInterest.class))).thenReturn(userInterestToUpdate);

		UserInterest actual = userInterestService.updateUserInterest(userInterestToUpdate);

		assertNotNull(actual);
		assertEquals(userInterestToUpdate.getInterest(), actual.getInterest());
	}

	@Test
	void findInterestsByUserId_WhenUserExistsAndHasInterests_ShouldReturnInterests() {
		Long userId = 1L;
		Interest interest1 = new Interest(1L, InterestType.CINEMA);
		Interest interest2 = new Interest(2L, InterestType.RESTAURANT);
		User user = new User();
		user.setId(userId);
		UserInterest userInterest1 = new UserInterest(1L, user, interest1);
		UserInterest userInterest2 = new UserInterest(2L, user, interest2);

		when(userRepository.existsById(userId)).thenReturn(true);
		when(userInterestRepository.findByUserId(userId)).thenReturn(Arrays.asList(userInterest1, userInterest2));
		when(userInterestRepository.findInterestsByUserId(userId)).thenReturn(Arrays.asList(interest1, interest2));

		List<Interest> interests = userInterestService.findInterestsByUserId(userId);

		assertNotNull(interests);
		assertEquals(2, interests.size());
		assertTrue(interests.contains(interest1));
		assertTrue(interests.contains(interest2));
	}

	@Test
	void givenUserId_whenFindingStringInterests_ShouldReturnInterestNames() {
		Long userId = 1L;
		User user = new User();
		user.setId(userId);
		Interest interest1 = new Interest(1L, InterestType.CINEMA);
		Interest interest2 = new Interest(2L, InterestType.RESTAURANT);
		UserInterest userInterest1 = new UserInterest(1L, user, interest1);
		UserInterest userInterest2 = new UserInterest(2L, user, interest2);

		when(userRepository.existsById(userId)).thenReturn(true);
		when(userInterestRepository.findByUserId(userId)).thenReturn(Arrays.asList(userInterest1, userInterest2));

		String interests = userInterestService.findStrInterestsByUserId(userId);

		assertNotNull(interests);
		assertEquals("CINEMA,RESTAURANT", interests);
	}
}
