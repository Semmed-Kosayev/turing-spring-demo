package com.semmed.turing.demo.mapper;

import com.semmed.turing.demo.domain.entity.UserEntity;
import com.semmed.turing.demo.model.dto.CreateUserRequest;
import com.semmed.turing.demo.model.dto.UpdateUserRequest;
import com.semmed.turing.demo.model.dto.UserDto;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(@NotNull UserEntity user);

    @Mapping(target = "id", ignore = true) // Ensure ID is ignored for creation
    @Mapping(target = "status", constant = "ACTIVE") // Set default status
    UserEntity toEntity(@NotNull CreateUserRequest request);

    @Mapping(target = "id", ignore = true) // Assume ID is not updated
    UserEntity toEntity(@NotNull UpdateUserRequest request);
}
