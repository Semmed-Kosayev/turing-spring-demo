package com.semmed.turing.demo.domain.entity;

import com.semmed.turing.demo.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Long id;
    private String username;
    private String email;
    private String password;
    private UserStatus status;
}
