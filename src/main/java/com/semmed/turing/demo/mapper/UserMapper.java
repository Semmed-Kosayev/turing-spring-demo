package com.semmed.turing.demo.mapper;

import com.semmed.turing.demo.domain.entity.UserEntity;
import com.semmed.turing.demo.model.dto.CreateUserRequest;
import com.semmed.turing.demo.model.dto.UpdateUserRequest;
import com.semmed.turing.demo.model.dto.UserDto;
import com.semmed.turing.demo.model.enums.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toUserDto(UserEntity user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus().name().toUpperCase()
        );
    }

    public UserEntity toEntity(CreateUserRequest request) {
        return UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .status(UserStatus.ACTIVE)
                .build();
    }

    public UserEntity toEntity(UpdateUserRequest request) {
        return UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .status(UserStatus.valueOf(request.status().toUpperCase()))
                .build();
    }
}
