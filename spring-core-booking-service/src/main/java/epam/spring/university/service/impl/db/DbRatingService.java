package epam.spring.university.service.impl.db;

import org.springframework.jdbc.core.JdbcTemplate;

import epam.spring.university.domain.EventRating;

public class DbRatingService
{
    private JdbcTemplate jdbcTemplate;

    public DbRatingService(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getId(EventRating rating)
    {
        String result = jdbcTemplate.queryForObject("SELECT id FROM ratings where name = ? ", new Object[]{rating.name()},
                String.class);
        if (result != null)
        {
            return Integer.parseInt(result);
        }
        return null;
    }

    public EventRating getById(int id)
    {
        String result = jdbcTemplate.queryForObject("SELECT name FROM ratings where id = " + id, String.class);
        return EventRating.valueOf(result);
    }
}
