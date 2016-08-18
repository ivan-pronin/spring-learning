package epam.spring.university.service.impl.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import epam.spring.university.domain.User;
import epam.spring.university.service.UserService;

public class DbUserServiceImpl implements UserService
{
    private JdbcTemplate jdbcTemplate;
    private DbConfigurer dbConfigurer;
    
    public DbUserServiceImpl(JdbcTemplate jdbcTemplate, DbConfigurer dbConfigurer)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.dbConfigurer = dbConfigurer;
    }

    @Override
    public User save(User object)
    {
        int rowsAffected = jdbcTemplate.update(
                "INSERT INTO users(firstName, lastName, email, birthDate) VALUES(?,?,?,?)", object.getFirstName(),
                object.getLastName(), object.getEmail(), object.getBirthDate());
        if (rowsAffected > 0)
        {
            return object;
        }
        return null;
    }

    @Override
    public void remove(User object)
    {
        jdbcTemplate.update("DELETE FROM users WHERE id= ?", object.getId());
    }

    @Override
    public User getById(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[] {id}, new UserRowMapper());
    }

    private class UserRowMapper implements RowMapper<User>
    {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String email = rs.getString("email");
            Date date = rs.getDate("birthDate");
            User user = new User(firstName, lastName, email);
            user.setBirthDate(date);
            return user;
        }
    }

    @Override
    public Set<User> getAll()
    {
        List<User> users = jdbcTemplate.query("SELECT * FROM users", new UserRowMapper());
        return new HashSet<>(users);
    }

    @Override
    public User getUserByEmail(String email)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", new Object[] {email},
                new UserRowMapper());
    }

    @Override
    public void removeAllUsers()
    {
        dbConfigurer.truncateTable("users");
    }
}
