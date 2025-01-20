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
import com.semmed.turing.demo.model.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;
import java.util.Optional;

import static com.semmed.turing.demo.constant.TestConstant.*;
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
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnSuccess_whenRepositoryReturnsUsers() {
        when(repo.findAll()).thenReturn(getUserList());

        List<UserDto> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(getUserDto1(), result.get(0));
        assertEquals(getUserDto2(), result.get(1));

        verify(repo, times(1)).findAll();
        verify(mapper, times(1)).toUserDto(getUser1());
        verify(mapper, times(1)).toUserDto(getUser2());
    }

    @Test
    void findAll_shouldReturnEmptyList_whenRepositoryReturnsEmptyList() {
        when(repo.findAll()).thenReturn(getEmptyUserList());

        List<UserDto> result = userService.findAll();

        assertEquals(0, result.size());
        verify(repo, times(1)).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    void findAll_shouldHandleMultipleCalls() {
        when(repo.findAll()).thenReturn(getUserList());

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
        when(repo.findById(1L)).thenReturn(Optional.of(getUser1()));

        UserDto result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(result, TestConstant.getUserDto1());

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
        CreateUserRequest createUserRequest = getCreateUserRequest();
        UserEntity createdUser = getCreatedUser();
        when(repo.existsByEmail(createUserRequest.email())).thenReturn(false);
        when(repo.existsByUsername(createUserRequest.username())).thenReturn(false);
        when(repo.save(mapper.toEntity(createUserRequest))).thenReturn(createdUser);

        UserDto result = userService.create(createUserRequest);

        assertNotNull(result);
        assertEquals(TestConstant.getCreatedUserDto(), result);
    }

    @Test
    void create_shouldThrowAlreadyExistsException_whenEmailAlreadyExists() {
        CreateUserRequest createUserRequest = TestConstant.getCreateUserRequest();
        when(repo.existsByEmail(createUserRequest.email())).thenReturn(true);

        AlreadyExistsException ex =
                assertThrows(AlreadyExistsException.class, () -> userService.create(createUserRequest));

        assertEquals(ex.getMessage(), "User with this email already exists");
    }

    @Test
    void create_shouldThrowAlreadyExistsException_whenUsernameAlreadyExists() {
        CreateUserRequest createUserRequest = TestConstant.getCreateUserRequest();
        when(repo.existsByUsername(createUserRequest.username())).thenReturn(true);

        AlreadyExistsException ex =
                assertThrows(AlreadyExistsException.class, () -> userService.create(createUserRequest));

        assertEquals(ex.getMessage(), "User with this username already exists");
    }

    @Test
    void update_shouldReturnSuccess() {
        UserEntity user3 = getUser3();
        when(repo.findById(3L)).thenReturn(Optional.of(user3));
        when(repo.save(user3)).thenReturn(getUpdateUserEntity());

        UserDto result = userService.update(3L, getUpdateUserRequest());

        assertNotNull(result);
        assertEquals(getUpdateUserDto(), result);
    }

    @Test
    void update_shouldThrowNotFoundException_whenUserNotExistsById() {
        when(repo.findById(3L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                userService.update(3L, getUpdateUserRequest()));
        assertEquals(ex.getMessage(), "User with specified id not found");
    }

    @Test
    void updateStatus_shouldReturnSuccess() {
        when(repo.findById(3L)).thenReturn(Optional.of(getUser3()));
        when(repo.save(getUser3Inactive())).thenReturn(getUser3Inactive());

        UserDto result = userService.updateStatus(3L, UserStatus.INACTIVE);

        assertNotNull(result);
        assertEquals(getUser3DtoInactive(), result);
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
        when(repo.findById(3L)).thenReturn(Optional.of(getUser3()));

        InvalidInputException ex =
                assertThrows(InvalidInputException.class, () -> userService.updateStatus(3L, UserStatus.ACTIVE));
        assertEquals(ex.getMessage(), "Same status cannot be sent");
    }
}