package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.repository.UserTypeRepository;
import com.mmhb.farketmez.type.UserTypeEnum;

class UserTypeServiceTest {

	@Mock
	private UserTypeRepository userTypeRepository;

	private UserTypeService userTypeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		userTypeService = new UserTypeService(userTypeRepository);
	}

	@Test
	void whenCreatingUserType_thenShouldReturnSavedUserType() {
		UserType userTypeToSave = new UserType(null, UserTypeEnum.ADMIN);
		when(userTypeRepository.save(any(UserType.class))).thenReturn(userTypeToSave);

		UserType actual = userTypeService.createUserType(userTypeToSave);

		assertNotNull(actual);
		assertEquals(userTypeToSave.getType(), actual.getType());
	}

	@Test
	void whenRetrievingAllUserTypes_thenShouldReturnListOfUserTypes() {
		List<UserType> userTypes = Arrays.asList(new UserType(1L, UserTypeEnum.ADMIN),
				new UserType(2L, UserTypeEnum.USER));
		when(userTypeRepository.findAll()).thenReturn(userTypes);

		List<UserType> actual = userTypeService.getAllUserTypes();

		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	void givenUserTypeId_whenRetrievingUserType_thenShouldReturnUserType() {
		Long userTypeId = 1L;
		Optional<UserType> userType = Optional.of(new UserType(userTypeId, UserTypeEnum.ADMIN));
		when(userTypeRepository.findById(userTypeId)).thenReturn(userType);

		UserType actual = userTypeService.getUserTypeById(userTypeId);

		assertNotNull(actual);
		assertEquals(userTypeId, actual.getId());
	}

	@Test
	void givenUserTypeDetails_whenUpdatingUserType_thenShouldReturnUpdatedUserType() {
		Long userTypeId = 1L;
		UserType userTypeToUpdate = new UserType(userTypeId, UserTypeEnum.USER);
		when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.of(userTypeToUpdate));
		when(userTypeRepository.save(any(UserType.class))).thenReturn(userTypeToUpdate);

		UserType actual = userTypeService.updateUserType(userTypeToUpdate);

		assertNotNull(actual);
		assertEquals(userTypeToUpdate.getType(), actual.getType());
	}

	@Test
	void givenUserTypeId_whenDeletingUserType_thenShouldDeleteUserType() {
		Long userTypeId = 1L;
		doNothing().when(userTypeRepository).deleteById(userTypeId);
		userTypeService.deleteUserType(userTypeId);
		verify(userTypeRepository).deleteById(userTypeId);
	}
}
