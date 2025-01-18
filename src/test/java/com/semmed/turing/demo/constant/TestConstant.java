package com.semmed.turing.demo.constant;

import com.semmed.turing.demo.domain.entity.UserEntity;
import com.semmed.turing.demo.model.dto.CreateUserRequest;
import com.semmed.turing.demo.model.dto.UpdateStatusRequest;
import com.semmed.turing.demo.model.dto.UpdateUserRequest;
import com.semmed.turing.demo.model.dto.UserDto;
import com.semmed.turing.demo.model.enums.UserStatus;

import java.util.List;

public interface TestConstant {
    UserEntity USER_1 = UserEntity.builder()
            .id(1L)
            .username("Semmed Kosayev")
            .email("semmedkosayev@gmail.com")
            .password("huuuuuuu32uh")
            .status(UserStatus.ACTIVE)
            .build();

    UserEntity USER_2 = UserEntity.builder()
            .id(2L)
            .username("Elxan Nagiyev")
            .email("elxannagiyev@gmail.com")
            .password("elxnag123")
            .status(UserStatus.ACTIVE)
            .build();

    UserEntity USER_3 = getUser3();

    UserDto USER_DTO_1 = new UserDto(
            1L,
            "Semmed Kosayev",
            "semmedkosayev@gmail.com",
            UserStatus.ACTIVE);

    UserDto USER_DTO_2 = new UserDto(
            2L,
            "Elxan Nagiyev",
            "elxannagiyev@gmail.com",
            UserStatus.ACTIVE);

    List<UserEntity> EMPTY_USER_LIST = List.of();
    List<UserEntity> USER_LIST = List.of(USER_1, USER_2);
    List<UserDto> USER_DTO_LIST = List.of(USER_DTO_1, USER_DTO_2);


    CreateUserRequest CREATE_USER_REQUEST = new CreateUserRequest(
            "Aysun Memmedova",
            "aysunmemmedova@gmail.com",
            "aysova455"
    );
    UserEntity CREATED_USER = UserEntity.builder()
            .id(3L)
            .username("Aysun Memmedova")
            .email("aysunmemmedova@gmail.com")
            .password("aysova455")
            .status(UserStatus.ACTIVE)
            .build();
    UserDto CREATED_USER_DTO = new UserDto(
            3L,
            "Aysun Memmedova",
            "aysunmemmedova@gmail.com",
            UserStatus.ACTIVE);

    UpdateUserRequest UPDATE_USER_REQUEST = new UpdateUserRequest(
            "Aysun Memmedova",
            "aysunmemmedova@gmail.com",
            "aysova456",
            UserStatus.ACTIVE
    );
    UserEntity UPDATE_USER_ENTITY = UserEntity.builder()
            .id(3L)
            .username("Aysun Memmedova")
            .email("aysunmemmedova@gmail.com")
            .password("aysova456")
            .status(UserStatus.ACTIVE)
            .build();
    UserDto UPDATE_USER_DTO = new UserDto(
            3L,
            "Aysun Memmedova",
            "aysunmemmedova@gmail.com",
            UserStatus.ACTIVE
    );
    UserEntity UPDATE_USER_ENTITY_INACTIVE = UserEntity.builder()
            .id(3L)
            .username("Aysun Memmedova")
            .email("aysunmemmedova@gmail.com")
            .password("aysova456")
            .status(UserStatus.INACTIVE)
            .build();
    UserDto UPDATE_USER_DTO_INACTIVE = new UserDto(
            3L,
            "Aysun Memmedova",
            "aysunmemmedova@gmail.com",
            UserStatus.INACTIVE
    );

    UserEntity USER_3_INACTIVE = getUser3Inactive();

    static UserEntity getUser3Inactive() {
        UserEntity user = new UserEntity();
        user.setId(3L);
        user.setUsername("Aysun Memmedova");
        user.setEmail("aysunmemmedova123@gmail.com");
        user.setPassword("456aysova");
        user.setStatus(UserStatus.INACTIVE);

        return user;
    }

    UserDto USER_DTO_3_INACTIVE = getUser3DtoInactive();

    static UserDto getUser3DtoInactive() {
        return new UserDto(
                3L,
                "Aysun Memmedova",
                "aysunmemmedova123@gmail.com",
                UserStatus.INACTIVE);
    }

    static UserEntity getUser3() {
        UserEntity user = new UserEntity();

        user.setId(3L);
        user.setUsername("Aysun Memmedova");
        user.setEmail("aysunmemmedova123@gmail.com");
        user.setPassword("456aysova");
        user.setStatus(UserStatus.ACTIVE);

        return user;
    }

    UpdateStatusRequest STATUS_REQUEST = new UpdateStatusRequest(UserStatus.INACTIVE);
}
