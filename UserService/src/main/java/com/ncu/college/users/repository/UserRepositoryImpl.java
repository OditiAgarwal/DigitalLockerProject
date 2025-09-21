package com.ncu.college.users.repository;

import com.ncu.college.users.irepository.IUserRepository;
import com.ncu.college.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", new UserRowMapper());
    }

    @Override
    public User getUserById(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM users WHERE id = ?",
            new Object[]{id},
            new UserRowMapper()
        );
    }

    // @Override
    // public User createUser(User user) {
    //     jdbcTemplate.update(
    //         "INSERT INTO users (name, email, phone, password_hash) VALUES (?, ?, ?, ?)",
    //         user.getName(), user.getEmail(), user.getPhone(), user.getPasswordHash()
    //     );
    //     return user;
    // }



    @Override
public User createUser(User user) {
    final String sql = "INSERT INTO users (name, email, phone, password_hash, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    Timestamp now = new Timestamp(System.currentTimeMillis());

    jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getPasswordHash());
        ps.setTimestamp(5, now);
        ps.setTimestamp(6, now);
        return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    if (key != null) {
        user.setId(key.intValue());
    }

    return user;
}


    @Override
    public User updateUser(int id, User user) {
        jdbcTemplate.update(
            "UPDATE users SET name = ?, email = ?, phone = ? WHERE id = ?",
            user.getName(), user.getEmail(), user.getPhone(), id
        );
        return user;
    }

    @Override
    public int deleteUser(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public List<User> searchUsersByName(String name) {
        return jdbcTemplate.query(
            "SELECT * FROM users WHERE name LIKE ?",
            new Object[]{"%" + name + "%"},
            new UserRowMapper()
        );
    }

    @Override
    public int updatePassword(int id, String newPasswordHash) {
        return jdbcTemplate.update(
            "UPDATE users SET password_hash = ? WHERE id = ?",
            newPasswordHash, id
        );
    }
}
