package com.semmed.turing.demo.domain.repository;

import com.semmed.turing.demo.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(final String email);

    boolean existsByUsername(final String username);

}
