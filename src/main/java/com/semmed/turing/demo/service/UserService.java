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
import com.semmed.turing.demo.model.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final UserMapper mapper;

    public List<UserDto> findAll() {
        List<UserEntity> all = repo.findAll();
        Stream<UserEntity> stream = all.stream();
        Function<UserEntity, UserDto> toUserDto = mapper::toUserDto;
        Stream<UserDto> userDtoStream = stream.map(toUserDto);
        List<UserDto> list = userDtoStream.toList();
        return list;
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
        UserEntity user = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));

        if (status.equals(user.getStatus())) {
            throw new InvalidInputException("Same status cannot be sent");
        }

        user.setStatus(status);
        UserEntity updatedUser = repo.save(user);

        return mapper.toUserDto(updatedUser);
    }

    public void deleteById(final long id) {
        UserEntity user = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));

        user.setStatus(UserStatus.DELETED);

        repo.save(user);
    }
}
