package com.mmhb.farketmez.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.mmhb.farketmez.dto.UserTypeDTO;
import com.mmhb.farketmez.mapper.UserTypeMapper;
import com.mmhb.farketmez.model.UserType;
import com.mmhb.farketmez.repository.UserTypeRepository;

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
		UserTypeDTO userTypeDTOToSave = new UserTypeDTO(null, "Admin");
		UserType savedUserType = UserTypeMapper.fromUserTypeDto(userTypeDTOToSave);
		when(userTypeRepository.save(any(UserType.class))).thenReturn(savedUserType);

		UserTypeDTO actual = userTypeService.createUserType(userTypeDTOToSave);

		assertNotNull(actual);
		assertEquals(userTypeDTOToSave.getType(), actual.getType());
	}

	@Test
	void whenRetrievingAllUserTypes_thenShouldReturnListOfUserTypes() {
		List<UserType> userTypes = Arrays.asList(new UserType(1L, "Admin"), new UserType(2L, "User"));
		when(userTypeRepository.findAll()).thenReturn(userTypes);

		List<UserTypeDTO> userTypeDTOs = userTypeService.getAllUserTypes();

		assertNotNull(userTypeDTOs);
		assertEquals(2, userTypeDTOs.size());
	}

	@Test
	void givenUserTypeId_whenRetrievingUserType_thenShouldReturnUserType() {
		Long userTypeId = 1L;
		Optional<UserType> userType = Optional.of(new UserType(userTypeId, "Admin"));
		when(userTypeRepository.findById(userTypeId)).thenReturn(userType);

		UserTypeDTO actual = userTypeService.getUserTypeById(userTypeId);

		assertNotNull(actual);
		assertEquals(userTypeId, actual.getId());
	}

	@Test
	void givenUserTypeDetails_whenUpdatingUserType_thenShouldReturnUpdatedUserType() {
		UserTypeDTO userTypeDTOToUpdate = new UserTypeDTO(1L, "Updated Type");
		UserType updatedUserType = UserTypeMapper.fromUserTypeDto(userTypeDTOToUpdate);
		when(userTypeRepository.existsById(userTypeDTOToUpdate.getId())).thenReturn(true);
		when(userTypeRepository.save(any(UserType.class))).thenReturn(updatedUserType);

		UserTypeDTO actual = userTypeService.updateUserType(userTypeDTOToUpdate);

		assertNotNull(actual);
		assertEquals(userTypeDTOToUpdate.getType(), actual.getType());
	}

	@Test
	void givenUserTypeId_whenDeletingUserType_thenShouldDeleteUserType() {
		Long userTypeId = 1L;
		doNothing().when(userTypeRepository).deleteById(userTypeId);
		userTypeService.deleteUserType(userTypeId);
		verify(userTypeRepository).deleteById(userTypeId);
	}

	@Test
	void givenNonExistentUserTypeId_whenRetrievingUserType_thenShouldReturnNull() {
		Long userTypeId = 1L;
		when(userTypeRepository.findById(userTypeId)).thenReturn(Optional.empty());

		UserTypeDTO actual = userTypeService.getUserTypeById(userTypeId);

		assertNull(actual);
	}
}
