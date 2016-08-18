package epam.spring.university.service.impl.db;

import org.springframework.jdbc.core.JdbcTemplate;

import epam.spring.university.service.impl.AopCounterService;

public class DbAopCounterServiceImpl implements AopCounterService
{
    private JdbcTemplate jdbcTemplate;

    public DbAopCounterServiceImpl(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertEventCounter(String counterName, int eventId, int newValue)
    {
        String sql = "INSERT INTO eventAopCounter(name, eventId, count) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, newValue, counterName, eventId);
    }
    
    @Override
    public void updateEventCounter(String counterName, int eventId, int newValue)
    {
        String sql = "UPDATE eventAopCounter SET count = ? WHERE name = ? AND eventId = ?";
        jdbcTemplate.update(sql, newValue, counterName, eventId);
    }

    @Override
    public void insertDiscountCounter(String counterName, int userId, int newValue)
    {
        String sql = "INSERT INTO discountAopCounter(name, userId, count) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, counterName, userId, newValue);
    }

    @Override
    public void updateDiscountCounter(String counterName, int userId, int newValue)
    {
        String sql = "UPDATE discountAopCounter SET count = ? WHERE name = ? AND userId = ?";
        jdbcTemplate.update(sql, newValue, counterName, userId);
    }
}
