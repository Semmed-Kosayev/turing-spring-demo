package com.semmed.turing.demo.service;

import com.semmed.turing.demo.constant.TestConstant;
import com.semmed.turing.demo.domain.entity.UserEntity;
import com.semmed.turing.demo.domain.repository.UserRepository;
import com.semmed.turing.demo.exception.AlreadyExistsException;
import com.semmed.turing.demo.exception.InvalidInputException;
import com.semmed.turing.demo.exception.NotFoundException;
import com.semmed.turing.demo.mapper.UserMapper;
import com.semmed.turing.demo.model.dto.CreateUserRequest;
import com.semmed.turing.demo.model.dto.UserDto;
import com.semmed.turing.demo.model.enums.UserField;
import com.semmed.turing.demo.model.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Spy
    private UserMapper mapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnSuccess_whenRepositoryReturnsUsers() {
        when(repo.findAll()).thenReturn(TestConstant.USER_LIST);

        List<UserDto> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TestConstant.USER_DTO_1, result.get(0));
        assertEquals(TestConstant.USER_DTO_2, result.get(1));

        verify(repo, times(1)).findAll();
        verify(mapper, times(1)).toUserDto(TestConstant.USER_1);
        verify(mapper, times(1)).toUserDto(TestConstant.USER_2);
    }

    @Test
    void findAll_shouldReturnEmptyList_whenRepositoryReturnsEmptyList() {
        when(repo.findAll()).thenReturn(TestConstant.EMPTY_USER_LIST);

        List<UserDto> result = userService.findAll();

        assertEquals(0, result.size());
        verify(repo, times(1)).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    void findAll_shouldHandleMultipleCalls() {
        when(repo.findAll()).thenReturn(TestConstant.USER_LIST);

        List<UserDto> firstCall = userService.findAll();
        List<UserDto> secondCall = userService.findAll();

        assertNotNull(firstCall);
        assertNotNull(secondCall);
        assertEquals(2, firstCall.size());
        assertEquals(2, secondCall.size());

        verify(repo, times(2)).findAll();
    }

    @Test
    void findById_shouldReturnSuccess_WhenUserExists() {
        when(repo.findById(1L)).thenReturn(Optional.of(TestConstant.USER_1));

        UserDto result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(result, TestConstant.USER_DTO_1);

        verify(repo, times(1)).findById(1L);
    }

    @Test
    void findById_shouldThrowNotFoundException_WhenUserNotExists() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> userService.findById(1L));
        assertEquals("User entity with specified id not found.", ex.getMessage());
    }

    @Test
    void create_shouldReturnSuccess() {
        CreateUserRequest createUserRequest = TestConstant.CREATE_USER_REQUEST;
        UserEntity createdUser = TestConstant.CREATED_USER;
        when(repo.existsByEmail(createUserRequest.email())).thenReturn(false);
        when(repo.existsByUsername(createUserRequest.username())).thenReturn(false);
        when(repo.save(mapper.toEntity(createUserRequest))).thenReturn(createdUser);

        UserDto result = userService.create(createUserRequest);

        assertNotNull(result);
        assertEquals(TestConstant.CREATED_USER_DTO, result);
    }

    @Test
    void create_shouldThrowAlreadyExistsException_whenEmailAlreadyExists() {
        CreateUserRequest createUserRequest = TestConstant.CREATE_USER_REQUEST;
        when(repo.existsByEmail(createUserRequest.email())).thenReturn(true);

        AlreadyExistsException ex =
                assertThrows(AlreadyExistsException.class, () -> userService.create(createUserRequest));

        assertEquals(ex.getMessage(), "User with this email already exists");
    }

    @Test
    void create_shouldThrowAlreadyExistsException_whenUsernameAlreadyExists() {
        CreateUserRequest createUserRequest = TestConstant.CREATE_USER_REQUEST;
        when(repo.existsByUsername(createUserRequest.username())).thenReturn(true);

        AlreadyExistsException ex =
                assertThrows(AlreadyExistsException.class, () -> userService.create(createUserRequest));

        assertEquals(ex.getMessage(), "User with this username already exists");
    }

    @Test
    void update_shouldReturnSuccess() {
        UserEntity user3 = TestConstant.USER_3;
        when(repo.findById(3L)).thenReturn(Optional.of(user3));
        when(repo.save(user3)).thenReturn(TestConstant.UPDATE_USER_ENTITY);

        UserDto result = userService.update(3L, TestConstant.UPDATE_USER_REQUEST);

        assertNotNull(result);
        assertEquals(TestConstant.UPDATE_USER_DTO, result);
    }

    @Test
    void update_shouldThrowNotFoundException_whenUserNotExistsById() {
        when(repo.findById(3L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                userService.update(3L, TestConstant.UPDATE_USER_REQUEST));
        assertEquals(ex.getMessage(), "User with specified id not found");
    }

    @Test
    void updateStatus_shouldReturnSuccess() {
        when(repo.findById(3L)).thenReturn(Optional.of(TestConstant.UPDATE_USER_ENTITY));
        when(repo.updateField(3L, UserField.STATUS, UserStatus.INACTIVE.name()))
                .thenReturn(TestConstant.UPDATE_USER_ENTITY_INACTIVE);

        UserDto result = userService.updateStatus(3L, UserStatus.INACTIVE);
        assertNotNull(result);
        assertEquals(TestConstant.UPDATE_USER_DTO_INACTIVE, result);
    }

    @Test
    void updateStatus_shouldThrowNotFoundException_whenUserNotExists() {
        when(repo.findById(3L)).thenReturn(Optional.empty());

        NotFoundException ex =
                assertThrows(NotFoundException.class, () -> userService.updateStatus(3L, UserStatus.INACTIVE));
        assertEquals(ex.getMessage(), "User with specified id not found");
    }

    @Test
    void updateStatus_shouldThrowInvalidInputException_whenStatusIsTheSame() {
        when(repo.findById(3L)).thenReturn(Optional.of(TestConstant.UPDATE_USER_ENTITY));

        InvalidInputException ex =
                assertThrows(InvalidInputException.class, () -> userService.updateStatus(3L, UserStatus.ACTIVE));
        assertEquals(ex.getMessage(), "Same status cannot be sent");
    }
}