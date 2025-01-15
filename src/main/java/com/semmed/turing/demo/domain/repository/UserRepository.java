package com.semmed.turing.demo.domain.repository;

import com.semmed.turing.demo.domain.entity.UserEntity;
import com.semmed.turing.demo.exception.NotFoundException;
import com.semmed.turing.demo.mapper.UserRowMapper;
import com.semmed.turing.demo.model.enums.UserField;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final NamedParameterJdbcTemplate template;
    private final UserRowMapper rowMapper;

    public List<UserEntity> findAll() {
        String sql = "SELECT * FROM users";
        return template.query(sql, rowMapper);
    }

    public Optional<UserEntity> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        UserEntity userById = template.queryForObject(sql, Map.of("id", id), rowMapper);
        return Optional.ofNullable(userById);
    }

    public Optional<UserEntity> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email";
        UserEntity userByEmail = template.queryForObject(sql, Map.of("email", email), rowMapper);
        return Optional.ofNullable(userByEmail);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE email = :email)";
        Boolean isExists = template.queryForObject(sql, Map.of("email", email), Boolean.class);
        return Boolean.TRUE.equals(isExists);
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE username = :username)";
        Boolean isExist = template.queryForObject(sql, Map.of("username", username), Boolean.class);
        return Boolean.TRUE.equals(isExist);
    }

    public boolean existsById(long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE id = :id)";
        Boolean isExists = template.queryForObject(sql, Map.of("id", id), Boolean.class);
        return Boolean.TRUE.equals(isExists);
    }

    @Transactional
    public UserEntity save(UserEntity user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("status", user.getStatus().name());

        String sql;
        if (existsByEmail(user.getEmail())) {
            sql = "UPDATE users " +
                    "SET username = :username, email = :email, password = :password, status = :status WHERE id = :id";

            params.addValue("id", user.getId());
        } else {
            sql = "INSERT INTO users (username, email, password, status) " +
                    "VALUES (:username, :email, :password, :status)";
        }

        template.update(sql, params);

        return findByEmail(user.getEmail())
                .orElseThrow(() -> new NotFoundException("User entity with specified email not found."));
    }

    @Transactional
    //replace String userField with Enum userField
    public UserEntity updateField(long userId, UserField userFieldName, String newFieldValue) {
        String updateSql =
                "UPDATE users SET %s = :newFieldValue WHERE id = :userId".formatted(userFieldName.getValue());

        Map<String, Object> params = Map.of("newFieldValue", newFieldValue, "userId", userId);

        template.update(updateSql, params);

        return findById(userId)
                .orElseThrow(() -> new NotFoundException("User entity with specified id not found."));
    }

    public void softDeleteById(long id) {
        updateField(id, UserField.STATUS, "DELETED");
    }

    @Transactional
    public void hardDeleteById(long id) {
        String deleteSql = "DELETE FROM users WHERE id = :id";

        template.update(deleteSql, Map.of("id", id));
    }
}
