package com.epam.springadvanced.repository.impl;

import static java.util.Optional.ofNullable;

import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.epam.springadvanced.entity.Role;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.UserRepository;
import com.epam.springadvanced.repository.WinsRepository;
import com.epam.springadvanced.utils.Convert;

@Repository
public class UserRepositoryImpl implements UserRepository
{
    private static final String UPDATE_USER_SET_NAME_EMAIL_BIRTH_DAY_PASSWORD = "UPDATE user SET name=?, email=?, "
            + "birthDay=?, password=? ";
    private static final String UPDATE_USER_BY_ID = UPDATE_USER_SET_NAME_EMAIL_BIRTH_DAY_PASSWORD + "WHERE id=?";
    private static final String UPDATE_USER_BY_NAME = UPDATE_USER_SET_NAME_EMAIL_BIRTH_DAY_PASSWORD + "WHERE name=?";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM user WHERE id=?";
    private static final String SELECT_BY_USER_EMAIL = "SELECT * FROM user WHERE email=?";
    private static final String SELECT_BY_USER_NAME = "SELECT * FROM user WHERE name=?";
    private static final String DELETE_TICKETS = "DELETE FROM tickets WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    private static final String DELETE_USER_ROLE = "DELETE FROM roles WHERE user_id=?";
    private static final String SELECT_ALL = "SELECT * FROM user";
    private static final String SELECT_USER_ROLES = "select * from role r\n" + "join roles rs on rs.role_id = r.id\n"
            + "where user_id=?";
    private static final String SELECT_ROLE_BY_NAME = "select * from role where name = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WinsRepository winsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User user)
    {
        if (user != null)
        {
            User updatedUser = user;
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            if (updatedUser.getId() != null)
            {
                // try update users by id
                jdbcTemplate.update(UPDATE_USER_BY_ID, user.getName(), user.getEmail(),
                        Convert.toTimestamp(user.getBirthday()), encodedPassword, user.getId());
            }
            else if (updatedUser.getName() != null && !updatedUser.getName().isEmpty())
            {
                // try update users by name
                jdbcTemplate.update(UPDATE_USER_BY_NAME, user.getName(), user.getEmail(),
                        Convert.toTimestamp(user.getBirthday()), encodedPassword, user.getName());
            }
            if (updatedUser.getId() == null)
            {
                // insert if users not saved yet
                SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user");
                insert.setGeneratedKeyName("id");
                Map<String, Object> args = new HashMap<>();
                args.put("name", updatedUser.getName());
                args.put("email", updatedUser.getEmail());
                args.put("birthDay", Convert.toTimestamp(user.getBirthday()));
                args.put("password", encodedPassword);
                updatedUser.setId(insert.executeAndReturnKey(args).longValue());
            }
            updateRoles(updatedUser.getId(), user.getRoles());
            return updatedUser;
        }
        return null;
    }

    private void updateRoles(long userId, List<Role> roles)
    {
        jdbcTemplate.update(DELETE_USER_ROLE, userId);
        for (Role role : roles)
        {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles");
            Map<String, Object> args = new HashMap<>();
            args.put("user_id", userId);
            args.put("role_id", getRoleByName(role.getName()).getId());
            insert.execute(args);
        }
    }

    private Role getRoleByName(String name)
    {
        try
        {
            return jdbcTemplate.queryForObject(SELECT_ROLE_BY_NAME, roleMapper(), name);
        }
        catch (EmptyResultDataAccessException ignored)
        {
            LOGGER.warn("No role was found by name: {}", name);
        }
        return null;
    }

    @Override
    public void delete(long id)
    {
        winsRepository.delete(id);
        jdbcTemplate.update(DELETE_TICKETS, id);
        jdbcTemplate.update(DELETE_USER_ROLE, id);
        jdbcTemplate.update(DELETE_USER, id);
    }

    @Override
    public User findById(long id)
    {
        try
        {
            return jdbcTemplate.queryForObject(SELECT_BY_USER_ID, userMapper(), id);
        }
        catch (EmptyResultDataAccessException ignored)
        {
            LOGGER.warn("No user was found by id: {}", id);
        }
        return null;
    }

    @Override
    public User findByEmail(String email)
    {
        try
        {
            return jdbcTemplate.queryForObject(SELECT_BY_USER_EMAIL, userMapper(), email);
        }
        catch (EmptyResultDataAccessException ignored)
        {
            LOGGER.warn("No user was found by email: {}", email);
        }
        return null;
    }

    @Override
    public User findByName(String name)
    {
        try
        {
            return jdbcTemplate.queryForObject(SELECT_BY_USER_NAME, userMapper(), name);
        }
        catch (EmptyResultDataAccessException ignored)
        {
            LOGGER.warn("No user was found by name: {}", name);
        }
        return null;
    }

    @Override
    public Collection<User> getAll()
    {
        return jdbcTemplate.query(SELECT_ALL, userMapper());
    }

    private List<Role> getRoles(long userId)
    {
        return jdbcTemplate.query(SELECT_USER_ROLES, roleMapper(), userId);
    }

    private RowMapper<Role> roleMapper()
    {
        return (rs, rowNum) ->
        {
            Role role = new Role();
            role.setId(rs.getInt(1));
            role.setName(rs.getString(2));
            return role;
        };
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private RowMapper<User> userMapper()
    {
        return (rs, rowNum) ->
        {
            User user = new User(rs.getLong(1), rs.getString(2), rs.getString(3),
                    ofNullable(rs.getDate(4)).map(Date::toLocalDate).orElseGet(null));
            user.setPassword(rs.getString(5));
            user.setRoles(getRoles(rs.getLong(1)));
            return user;
        };
    }
}
