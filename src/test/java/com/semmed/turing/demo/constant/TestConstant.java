package com.semmed.turing.demo.constant;

import com.semmed.turing.demo.domain.entity.UserEntity;
import com.semmed.turing.demo.model.dto.CreateUserRequest;
import com.semmed.turing.demo.model.dto.UpdateStatusRequest;
import com.semmed.turing.demo.model.dto.UpdateUserRequest;
import com.semmed.turing.demo.model.dto.UserDto;
import com.semmed.turing.demo.model.enums.UserStatus;

import java.util.List;

public interface TestConstant {

    static UserEntity getUser1() {
        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setUsername("Semmed Kosayev");
        user.setEmail("semmedkosayev@gmail.com");
        user.setPassword("huuuuuuu32uh");
        user.setStatus(UserStatus.ACTIVE);

        return user;
    }

    static UserEntity getUser2() {
        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setUsername("Elxan Nagiyev");
        user.setEmail("elxannagiyev@gmail.com");
        user.setPassword("elxnag123");
        user.setStatus(UserStatus.ACTIVE);

        return user;
    }

    static UserDto getUserDto1() {
        return new UserDto(
                1L,
                "Semmed Kosayev",
                "semmedkosayev@gmail.com",
                UserStatus.ACTIVE);
    }

    static UserDto getUserDto2() {
        return new UserDto(
                2L,
                "Elxan Nagiyev",
                "elxannagiyev@gmail.com",
                UserStatus.ACTIVE);
    }

    static List<UserEntity> getEmptyUserList() {
        return List.of();
    }

    static List<UserEntity> getUserList() {
        return List.of(getUser1(), getUser2());
    }

    static List<UserDto> getUserDtoList() {
        return List.of(getUserDto2(), getUserDto2());
    }

    static CreateUserRequest getCreateUserRequest() {
        return new CreateUserRequest(
                "Aysun Memmedova",
                "aysunmemmedova@gmail.com",
                "aysova455"
        );
    }

    static UserEntity getCreatedUser() {
        UserEntity user = new UserEntity();

        user.setId(3L);
        user.setUsername("Aysun Memmedova");
        user.setEmail("aysunmemmedova@gmail.com");
        user.setPassword("aysova455");
        user.setStatus(UserStatus.ACTIVE);

        return user;
    }

    static UserDto getCreatedUserDto() {
        return new UserDto(
                3L,
                "Aysun Memmedova",
                "aysunmemmedova@gmail.com",
                UserStatus.ACTIVE);
    }

    static UpdateUserRequest getUpdateUserRequest() {
        return new UpdateUserRequest(
                "Aysun Memmedova",
                "aysunmemmedova@gmail.com",
                "aysova456",
                UserStatus.ACTIVE
        );
    }

    static UserEntity getUpdateUserEntity() {
        UserEntity user = new UserEntity();

        user.setId(3L);
        user.setUsername("Aysun Memmedova");
        user.setEmail("aysunmemmedova@gmail.com");
        user.setPassword("aysova456");
        user.setStatus(UserStatus.ACTIVE);

        return user;
    }

    static UserDto getUpdateUserDto() {
        return new UserDto(
                3L,
                "Aysun Memmedova",
                "aysunmemmedova@gmail.com",
                UserStatus.ACTIVE
        );
    }

    static UserDto getUpdateUserDtoInactive() {
        return new UserDto(
                3L,
                "Aysun Memmedova",
                "aysunmemmedova@gmail.com",
                UserStatus.INACTIVE
        );
    }

    static UserEntity getUser3Inactive() {
        UserEntity user = new UserEntity();
        user.setId(3L);
        user.setUsername("Aysun Memmedova");
        user.setEmail("aysunmemmedova123@gmail.com");
        user.setPassword("456aysova");
        user.setStatus(UserStatus.INACTIVE);

        return user;
    }

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

    static UpdateStatusRequest getStatusRequest() {
        return new UpdateStatusRequest(UserStatus.INACTIVE);
    }
}
