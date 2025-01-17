package com.semmed.turing.demo.service;

import com.semmed.turing.demo.domain.entity.UserEntity;
import com.semmed.turing.demo.domain.repository.UserRepository;
import com.semmed.turing.demo.exception.AlreadyExistsException;
import com.semmed.turing.demo.exception.InvalidInputException;
import com.semmed.turing.demo.exception.NotFoundException;
import com.semmed.turing.demo.mapper.UserMapper;
import com.semmed.turing.demo.model.dto.CreateUserRequest;
import com.semmed.turing.demo.model.dto.UpdateUserRequest;
import com.semmed.turing.demo.model.dto.UserDto;
import com.semmed.turing.demo.model.enums.UserField;
import com.semmed.turing.demo.model.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final UserMapper mapper;

    public List<UserDto> findAll() {
        return repo.findAll().stream()
                .map(mapper::toUserDto)
                .toList();
    }

    public UserDto findById(final long id) {
        UserEntity user = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("User entity with specified id not found."));

        return mapper.toUserDto(user);
    }

    public UserDto create(final CreateUserRequest request) {
        if (repo.existsByEmail(request.email())) {
            throw new AlreadyExistsException("User with this email already exists");
        }

        if (repo.existsByUsername(request.username())) {
            throw new AlreadyExistsException("User with this username already exists");
        }

        UserEntity createdUser = repo.save(mapper.toEntity(request));

        return mapper.toUserDto(createdUser);
    }

    public UserDto update(final long id, final UpdateUserRequest request) {
        UserEntity user = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setStatus(request.status());

        return mapper.toUserDto(repo.save(user));
    }

    public UserDto updateStatus(final long id, final UserStatus status) {
        UserStatus userStatus = repo.findById(id)
                .map(UserEntity::getStatus)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));
        if (status.equals(userStatus)) {
            throw new InvalidInputException("Same status cannot be sent");
        }

        UserEntity updatedUser = repo.updateField(id, UserField.STATUS, status.name());

        return mapper.toUserDto(updatedUser);
    }

    public void deleteById(final long id) {
        checkUserExistence(id);

        repo.softDeleteById(id);
    }

    private void checkUserExistence(final long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("User with specified id not found");
        }
    }
}
