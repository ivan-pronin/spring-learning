package epam.spring.university.service.impl.db;

import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

public class DbDatesService
{
    private JdbcTemplate jdbcTemplate;

    public DbDatesService(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Date getById(int id)
    {
        java.sql.Date result = jdbcTemplate.queryForObject("SELECT date FROM dates WHERE id = " + id, java.sql.Date.class);
        if (result != null)
        {
            return new Date(result.getTime());
        }
        return null;
    }

    public Integer getDateId(Date date)
    {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        Integer result = jdbcTemplate.queryForObject("SELECT id FROM dates where date = ?", new Object[]{sqlDate}, Integer.class);
        return result;
    }
}
