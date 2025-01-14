package com.semmed.turing.demo.domain.repository;

import com.semmed.turing.demo.domain.entity.UserEntity;
import com.semmed.turing.demo.exception.NotFoundException;
import com.semmed.turing.demo.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate template;
    private final UserRowMapper rowMapper;

    public List<UserEntity> findAll() {
        String sql = "SELECT * FROM users";
        return template.query(sql, rowMapper);
    }

    public Optional<UserEntity> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return Optional.ofNullable(template.queryForObject(sql, rowMapper, id));
    }

    public UserEntity getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("User entity with specified id not found."));
    }

    public Optional<UserEntity> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return Optional.ofNullable(template.queryForObject(sql, rowMapper, email));
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE email = ?)";
        return Boolean.TRUE.equals(template.queryForObject(sql, new Object[]{email}, Boolean.class));
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE username = ?)";
        return Boolean.TRUE.equals(template.queryForObject(sql, new Object[]{username}, Boolean.class));
    }

    public boolean existsById(long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE id = ?)";
        return Boolean.TRUE.equals(template.queryForObject(sql, new Object[]{id}, Boolean.class));
    }

    @Transactional
    public UserEntity save(UserEntity user) {
        if (existsByEmail(user.getEmail())) {
            String updateSql = "UPDATE users SET username = ?, email = ?, password = ?, status = ? WHERE id = ?";

            template.update(
                    updateSql,
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getStatus().name(),
                    user.getId()
            );
        } else {
            String insertSql = "INSERT INTO users (username, email, password, status) VALUES (?, ?, ?, ?)";

            template.update(
                    insertSql,
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getStatus().name()
            );
        }

        return findByEmail(user.getEmail()).orElseThrow(() ->
                new NotFoundException("User entity with specified email not found."));
    }

    @Transactional
    //replace String userField with Enum userField
    public UserEntity updateField(long userId, String userField, String newValue) {
        String updateSql = "UPDATE users SET %s = ? WHERE id = ?".formatted(userField);
        template.update(updateSql, newValue, userId);
        return getById(userId);
    }

    public void softDeleteById(long id) {
        updateField(id, "status", "DELETED");
    }

    @Transactional
    public void hardDeleteById(long id) {
        String deleteSql = "DELETE FROM users WHERE id = ?";

        template.update(deleteSql, id);
    }
}
