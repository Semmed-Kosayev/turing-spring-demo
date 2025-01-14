package com.semmed.turing.demo.mapper;

import com.semmed.turing.demo.domain.entity.UserEntity;
import com.semmed.turing.demo.model.enums.UserStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<UserEntity> {
    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserEntity(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                UserStatus.valueOf(rs.getString("status"))
        );
    }
}
